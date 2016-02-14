package com.mvrt.frc2016.system;

import com.mvrt.lib.components.DriveTrain;
import com.mvrt.lib.control.misc.DriveSignal;
import com.mvrt.lib.util.Values;

import java.util.function.DoubleFunction;

/**
 * Class which interprets drive commands into actual motor output.
 * <br>
 * Can be used for a multitude of drive styles.
 *
 * @author Lee Mracek
 */
public class DriveInterpreter {

  private static final double SENSITIVITY = 0.75;

  private final DriveTrain driveTrain;

  private DoubleFunction<Double> limiter = Values.limiter(-1.0, 1.0);

  private boolean flip = false;

  public boolean getFlip() {
    return flip;
  }

  public void flip(boolean toFlip) {
    this.flip = toFlip;
  }

  /**
   * Construct a new DriveInterpreter wrapper around an {@link DriveTrain}.
   *
   * @param driveTrain the drivetrain to write to
   */
  public DriveInterpreter(DriveTrain driveTrain) {
    this.driveTrain = driveTrain;
  }

  /**
   * Stop the {@link DriveTrain}.
   */
  public void stop() {
    drive(DriveSignal.NEUTRAL);
  }

  /**
   * Drive the {@link DriveTrain} in an Arcade drive style.
   *
   * @param driveSpeed the forward drive speed signal
   * @param turnSpeed  the turn speed signal
   */
  public void arcade(double driveSpeed, double turnSpeed) {
    arcade(driveSpeed, turnSpeed, true);
  }


  /**
   * Drive the {@link DriveTrain} in an Arcade drive style.
   *
   * @param driveSpeed    the forward drive speed signal
   * @param turnSpeed     the turn speed signal
   * @param squaredInputs whether or not the inputs should be squared for linearity
   */
  public void arcade(double driveSpeed, double turnSpeed, boolean squaredInputs) {
    double leftMotorSpeed;
    double rightMotorSpeed;

    driveSpeed = limiter.apply(driveSpeed);
    turnSpeed = limiter.apply(turnSpeed);

    if (squaredInputs) {
      squareInputs(driveSpeed);

      squareInputs(turnSpeed);
    }

    if (driveSpeed > 0.0) {
      if (turnSpeed > 0.0) {
        leftMotorSpeed = driveSpeed - turnSpeed;
        rightMotorSpeed = Math.max(driveSpeed, turnSpeed);
      } else {
        leftMotorSpeed = Math.max(driveSpeed, -turnSpeed);
        rightMotorSpeed = driveSpeed + turnSpeed;
      }
    } else {
      if (turnSpeed > 0.0) {
        leftMotorSpeed = -Math.max(-driveSpeed, turnSpeed);
        rightMotorSpeed = driveSpeed + turnSpeed;
      } else {
        leftMotorSpeed = driveSpeed - turnSpeed;
        rightMotorSpeed = -Math.max(-driveSpeed, -turnSpeed);
      }
    }

    drive(new DriveSignal(leftMotorSpeed, rightMotorSpeed));
  }

  public double squareInputs(double speed) {
    return Math.signum(speed) * speed * speed;
  }

  /**
   * Drive the {@link DriveTrain} in a tank configuration.
   *
   * @param leftSpeed     the left signal for the drive
   * @param rightSpeed    the right signal of the drive
   * @param squaredInputs whether or not the drive inputs should be squared
   */
  public void tank(double leftSpeed, double rightSpeed, boolean squaredInputs) {
    leftSpeed = limiter.apply(leftSpeed);
    rightSpeed = limiter.apply(rightSpeed);

    if (squaredInputs) {
      leftSpeed = squareInputs(leftSpeed);

      rightSpeed = squareInputs(rightSpeed);
    }

    drive(new DriveSignal(leftSpeed, rightSpeed));
  }

  /**
   * Drive the {@link DriveTrain} in a tank configuration.
   *
   * @param leftSpeed  the left signal for the drive
   * @param rightSpeed the right signal for the drive
   */
  public void tank(double leftSpeed, double rightSpeed) {
    leftSpeed = limiter.apply(leftSpeed);
    rightSpeed = limiter.apply(rightSpeed);
    drive(new DriveSignal(leftSpeed, rightSpeed));
  }

  private double oldWheel = 0.0;
  private double negativeInertiaAccumulator = 0.0;
  private double quickStopAccumulator = 0.0;

  /**
   * Drive the {@link DriveTrain} in the Austin/Cheesy style. Originally pioneered by Austin
   * Schuh in 2008, this style of drive has been stolen 254's public repos by practically every
   * single team.
   *
   * @param throttle  the throttle to drive with
   * @param wheel     the wheel to drive with
   * @param quickturn whether or not to apply quickturn
   */
  public void austinDrive(double throttle, double wheel, boolean quickturn) {
    wheel = limiter.apply(wheel);
    throttle = limiter.apply(throttle);

    double negativeInertia = wheel - oldWheel;
    oldWheel = wheel;

    double wheelNonLinearity = 0.6; // tune this
    wheel = dampen(wheel, wheelNonLinearity);
    wheel = dampen(wheel, wheelNonLinearity);

    double leftPwm, rightPwm, overPower;
    double sensitivity = SENSITIVITY;

    double angularPower;
    double linearPower;

    double negativeInertiaScalar;

    if (wheel * negativeInertia > 0) {
      negativeInertiaScalar = 2.5;
    } else {
      if (Math.abs(wheel) > 0.65) {
        negativeInertiaScalar = 5.0;
      } else {
        negativeInertiaScalar = 3.0;
      }
    }

    double negativeInertiaPower = negativeInertia * negativeInertiaScalar;
    negativeInertiaAccumulator += negativeInertiaPower;

    wheel += negativeInertiaPower;

    if (negativeInertiaAccumulator > 1) {
      negativeInertiaAccumulator -= 1;
    } else if (negativeInertiaAccumulator < -1) {
      negativeInertiaAccumulator += 1;
    } else {
      negativeInertiaAccumulator = 0;
    }

    linearPower = throttle;

    if (quickturn) {
      if (Math.abs(linearPower) > 0.2) {
        double alpha = 0.1;
        quickStopAccumulator =
            (1 - alpha) * quickStopAccumulator + alpha * Values.limiter(0.0, 1.0).apply(wheel) * 5;
      }
      overPower = 1.0;
      sensitivity = 1.0;
      angularPower = wheel;
    } else {
      overPower = 0.0;
      angularPower = Math.abs(throttle) * wheel * sensitivity - quickStopAccumulator;
      if (quickStopAccumulator > 1) {
        quickStopAccumulator -= 1.0;
      } else if (quickStopAccumulator < -1) {
        quickStopAccumulator += 0.0;
      } else {
        quickStopAccumulator = 0.0;
      }
    }

    rightPwm = leftPwm = linearPower;
    leftPwm += angularPower;
    rightPwm -= angularPower;

    if (leftPwm > 1.0) {
      rightPwm -= overPower * (leftPwm - 1.0);
      leftPwm = 1.0;
    } else if (rightPwm > 1.0) {
      leftPwm -= overPower * (rightPwm - 1.0);
      rightPwm = 1.0;
    } else if (leftPwm < -1.0) {
      rightPwm += overPower * (-1.0 - leftPwm);
      leftPwm = -1.0;
    } else if (rightPwm < -1.0) {
      leftPwm += overPower * (-1.0 - rightPwm);
      rightPwm = -1.0;
    }

    drive(new DriveSignal(leftPwm, rightPwm));
  }

  private void drive(DriveSignal signal) {
    if(flip) {
      driveTrain.drive(signal.invert());
    } else {
      driveTrain.drive(signal);
    }
  }

  private static double dampen(double wheel, double wheelNonLinearity) {
    double factor = Math.PI * wheelNonLinearity;
    return Math.sin(factor * wheel) / Math.sin(factor);
  }
}
