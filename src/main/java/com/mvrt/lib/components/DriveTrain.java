package com.mvrt.lib.components;

/**
 * This interface is an abstract representation of a DriveTrain which has the
 * ability to control both motors.
 *
 * @author Lee Mracek
 */
public interface DriveTrain {
  void drive(double leftMotor, double rightMotor);

  /**
   * Construct a new DriveTrain object from a left and right motor.
   *
   * @param left  the left motor
   * @param right the right motor
   * @return the newly constructed DriveTrain
   */
  static DriveTrain create(Motor left, Motor right) {
    if (left == null) {
      throw new IllegalArgumentException("Left Motor cannot be null");
    }
    if (right == null) {
      throw new IllegalArgumentException("Right Motor cannot be null");
    }

    return ((leftMotor, rightMotor) -> {
      left.setSpeed(leftMotor);
      right.setSpeed(rightMotor);
    });
  }
}
