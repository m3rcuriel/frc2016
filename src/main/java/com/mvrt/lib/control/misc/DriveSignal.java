package com.mvrt.lib.control.misc;

/**
 * DriveSignal to abstractly represent a motor command.
 *
 * @author Lee Mracek
 */
public class DriveSignal {
  public double leftMotor, rightMotor;

  public DriveSignal(double leftMotor, double rightMotor) {
    this.leftMotor = leftMotor;
    this.rightMotor = rightMotor;
  }

  public DriveSignal invert() {
    this.leftMotor = -1 * leftMotor;
    this.rightMotor = -1 * rightMotor;
    return this;
  }

  public static DriveSignal NEUTRAL = new DriveSignal(0.0, 0.0);
}
