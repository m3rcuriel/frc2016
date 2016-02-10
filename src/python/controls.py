import scipy.signal.cont2discrete
import numpy
import slycot
import glog


def continuous_to_discrete(A, B, dt):
    """ Converts from continuous time state-space representation to a discrete
    time implementation. Returns (A, B)."""

    disc_a, disc_b, _, _, _= scipy.signal.cont2discrete((A, B, None, None), dt)
    return numpy.matrix(disc_a), numpy.matrix(disc_b)


def controllability(A, B):
    """Returns the controllability matrix.

    Must have full rank for all states to be controllable.
    """
    n = A.shape[0]
    output = B
    intermediate = B
    for i in xrange(0, n):
        intermediate = A * intermediate
        output = numpy.concatenate((output, intermediate), axis=1)

    return output


def do_lqr(A, B, Q, R):
    """ Compute the optimal LQR controller.
        x (n + 1) = A * x (n) + B * u(n)
        J = sum(0, inf, x.T * Q * x + u.T * R * u)
    """

    P, r_conditions, w, S, T = slycot.sb02od(n=A.shape[0], m=B.shape[1], A=A,
                                             B=B, Q=Q, R=R, dico='D')
    F = numpy.linalg.inv(R + B.T * P * B) * B.T * P * A
    return F


def kalman(A, B, C, Q, R):
    """ Solves for steady state kalman gain and covariance.

    Args:
        A, B, C: The state matrices of the system
        Q: The model uncertainty
        R: The measurement uncertainty

    Returns:
        KalmanGain, Covariance
    """
    P = Q

    I = numpy.matrix(numpy.eye(P.shape[0]))
    At = A.T
    Ct = C.T

    while True:
        last_P = P
        P_prior = A * P * At + Q
        S = C * P_prior * Ct + R
        K = P_prior * Ct * numpy.linalg.inv(S)
        P = (I - K * C) * P_prior

        diff = P - last_P
        if numpy.linalg.norm(diff) / numpy.linalg.norm(P) < 1e-9:
            break

    return K, P
