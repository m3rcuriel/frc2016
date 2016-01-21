package com.mvrt.lib.control.misc;

/**
 * Created by lee on 1/21/16.
 */
public class PidConstants {
  public double kP;
  public double kI;
  public double kD;

  public PidConstants(double kP, double kI, double kD) {
    this.kP = kP;
    this.kI = kI;
    this.kD = kD;
  }
}
