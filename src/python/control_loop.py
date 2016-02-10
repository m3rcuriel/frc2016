import controls
import numpy
import scipy


class ControlLoop(object):

    def continuous_to_discrete(self, A_continuous, B_continuous, dt):
        """ Transforms the A and B matrices to the discrete form.
         Args:
            A_continuous: numpy.matrix, the continuous time A matrix
            B_continuous: numpy.matrix, the continuous time B matrix
            dt: float, The time step of the control loop

        Returns:
            (A, B), numpy.matrix, the control matrices.
        """
        return controls.continuous_to_discrete(A_continuous, B_continuous, dt)

    def InitializeState(self):
        """Sets X, Y, and X_hat to zero defaults."""
        self.X = numpy.zeros((self.A.shape[0], 1))
        self.Y = self.C * self.X
        self.X_hat = numpy.zeros((self.A.shape[0], 1))

    def Update(self, U):
        """Simulates a single time step for the provided U"""
        self.X = self.A * self.X + self.B * U
        self.Y = self.C * self.X + self.D * U

    def PredictObserver(self, U):
        """Predicts the next state using the observer"""
        self.X_hat = (self.A * self.X_hat + self.B * U)

    def CorrectObserver(self, U):
        """Runs the correct step of the observer update"""
        self.X_hat += numpy.linalg.inv(self.A) * self.L * \
            (self.Y - self.C * self.X_hat - self.D * U)

    def UpdateObserver(self, U):
        """Update the observer given the provided U"""
        self.X_hat = (self.A * self.X_hat + self.B * U +
                      self.L * (self.Y - self.C * self.X_hat - self.D * U))
