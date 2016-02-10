#!/usr/bin/python

import control_loop
import controls
import numpy
import glog
import sys
from matplotlib import pylab


class Shooter(control_loop.ControlLoop):
    def __init__(self):
        # Stall torque in N m
        self.stall_torque = 0.71
        # Stall current in A
        self.stall_current = 134
        # Free speed in RPM
        self.free_speed = 18700
        # Free current in Amps
        self.free_current = 0.7
        # Moment of inertia in kg m^s
        self.J = 0.0032
        # Resistance of the motor
        self.R = 12.0 / self.stall_current
        # Motor velocity constant
        self.Kv = ((self.free_speed / 60.0 * 2.0 * numpy.pi) *
                   (12.0 - self.R * self.free_current))
        # Motor torque constant
        self.Kt = self.stall_torque / self.stall_current
        # Gear ratio
        self.G = 1.0 / 3.0
        # Control loop time step
        self.dt = 0.005

        # State matrices
        self.A_continuous = numpy.matrix(
            [[0, 1],
             [0, -self.Kt / self.Kv / (self.J * self.G * self.G * self.R)]])
        self.B_continuous = numpy.matrix(
            [[0],
             [self.Kt / (self.J * self.G * self.R)]])
        self.C = numpy.matrix([[1, 0]])
        self.D = numpy.matrix([[0]])

        (self.A, self.B) = self.continuous_to_discrete(self.A_continuous,
                                                       self.B_continuous,
                                                       self.dt)

        controllability = controls.controllability(self.A, self.B)

        if numpy.linalg.matrix_rank(controllability) != self.A.shape[0]:
            glog.warn("Controllability matrix indicates the system cannot" +
                      "be fully controlled")

        q_pos = 0.1
        q_vel = 0.05
        self.Q = numpy.matrix([[(1.0 / (q_pos ** 2.0)), 0.0],
                               [0.0, (1.0 / (q_vel ** 2.0))]])

        self.R = numpy.matrix([[(1.0 / (12.0 ** 2.0))]])

        self.K = controls.do_lqr(self.A, self.B, self.Q, self.R)

        glog.info('K %s', str(self.K))
        glog.info('Poles are %s',
                  str(numpy.linalg.eig(self.A - self.B * self.K)[0]))

        self.U_max = numpy.matrix([12])
        self.U_min = numpy.matrix([-12])

        q_pos = 0.5
        q_vel = 0.01
        self.Q = numpy.matrix([[(q_pos ** 2.0), 0.0],
                              [0.0, (q_vel ** 2.0)]])

        r_volts = 0.025
        self.R = numpy.matrix([[(r_volts ** 2.0)]])

        self.KalmanGain, self.Q_steady = controls.kalman(A=self.A, B=self.B,
                                                         C=self.C, Q=self.Q,
                                                         R=self.R)

        self.L = self.A * self.KalmanGain
        glog.info('KalL is %s', str(self.L))

        self.InitializeState()


class ScenarioPlotter:
    def __init__(self):
        self.t = []
        self.x = []
        self.v = []
        self.a = []
        self.x_hat = []
        self.u = []
        self.R_goal = []

    def run_test(self, shooter, goal, iterations=200, controller_shooter=None,
                 observer_shooter=None):
        """Runs the shooter plant with an initial condition and goal.

        Args:
            Shooter: shooter object to use
            initial_X: starting state
            goal: goal state
            iterations: Number of timesteps to run the model for
            controller_shooter: the shooter object to pull K from
            observer_shooter: the shooter object to use for observer
        """

        if controller_shooter is None:
            controller_shooter = shooter

        vbat = 12.0
        if self.t:
            initial_t = self.t[-1] + shooter.dt
        else:
            initial_t = 0

        velocity_goal = goal[1, 0]

        R = goal
        for i in xrange(iterations):
            X_hat = shooter.X
            if observer_shooter is not None:
                X_hat = observer_shooter.X_hat
            self.x_hat.append(X_hat[1, 0])

            R = numpy.matrix([[R[0, 0] + 10.5], [R[1, 0]]])

            self.R_goal.append(R[0, 0])

            velocity_weight_scalar = 0.35
            max_reference = (
                (shooter.U_max[0, 0] - velocity_weight_scalar *
                    (velocity_goal - X_hat[1, 0]) * shooter.K[0, 1]) /
                shooter.K[0, 0] +
                X_hat[0, 0])
            min_reference = (
                (shooter.U_min[0, 0] - velocity_weight_scalar *
                    (velocity_goal - X_hat[1, 0]) * shooter.K[0, 1]) /
                shooter.K[0, 0] +
                X_hat[0, 0])

            R[0, 0] = numpy.clip(R[0, 0], min_reference, max_reference)
            U = numpy.clip(shooter.K * (R - X_hat),
                           -vbat, vbat)

            self.x.append(shooter.X[0, 0])
            if self.v:
                last_v = self.v[-1]
            else:
                last_v = 0
            self.v.append(shooter.X[1, 0])
            self.a.append((self.v[-1] - last_v) / shooter.dt)

            if observer_shooter is not None:
                observer_shooter.Y = shooter.Y
                observer_shooter.CorrectObserver(U)

            shooter.Update(U)

            if observer_shooter is not None:
                observer_shooter.PredictObserver(U)

            self.t.append(initial_t + i * shooter.dt)
            self.u.append(U[0, 0])

        glog.debug('Time: %f', self.t[-1])

    def plot(self):
        pylab.subplot(3, 1, 1)
        pylab.plot(self.t, self.v, label='v')
        pylab.plot(self.t, self.x_hat, label='v')
        pylab.legend()

        pylab.subplot(3, 1, 2)
        pylab.plot(self.t, self.u, label='u')
        pylab.legend()

        pylab.subplot(3, 1, 3)
        pylab.plot(self.t, self.x, label='x')
        pylab.plot(self.t, self.R_goal, label='R')
        pylab.legend()

        pylab.show()


def main():
    shooter = Shooter()
    observer_shooter = Shooter()

    initial_X = numpy.matrix([[0.0], [0.0]])
    R = numpy.matrix([[0.0], [100]])

    scenario_plotter = ScenarioPlotter()

    shooter.X = initial_X
    scenario_plotter.run_test(shooter, goal=R,
                              observer_shooter=observer_shooter)

    scenario_plotter.plot()


if __name__ == '__main__':
    sys.exit(main())
