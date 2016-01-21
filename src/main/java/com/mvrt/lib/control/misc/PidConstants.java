package com.mvrt.lib.control.misc;

/**
 * Container for a set of PID constants.
 *
 * @author Lee Mracek
 */
public class PidConstants {
  /**
   * The proportional constant.
   */
  public double kP;

  /**
   * The integral constant.
   */
  public double kI;

  /**
   * The derivative constant.
   */
  public double kD;

  /**
   * Construct a new container based on initial values.
   *
   * @param kP the proportional constant
   * @param kI the integral constant
   * @param kD the derivative constant
   */
  public PidConstants(double kP, double kI, double kD) {
    this.kP = kP;
    this.kI = kI;
    this.kD = kD;
  }

  /**
   * Construct a zeroed container.
   */
  public PidConstants() {
    this.kP = 0;
    this.kI = 0;
    this.kD = 0;
  }
}
