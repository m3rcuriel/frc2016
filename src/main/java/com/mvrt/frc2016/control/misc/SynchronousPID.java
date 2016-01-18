package com.mvrt.frc2016.control.misc;

import edu.wpi.first.wpilibj.util.BoundaryException;

/**
 * Created by siddharth on 1/17/16.
 */
public class SynchronousPid {

  private double cP;
  private double cI;
  private double cD;
  private double maxOutput;
  private double minOutput;
  private double maxInput;
  private double minInput;
  private boolean continuous = false;
  private double previousError = 0.0;
  private double totalError = 0.0;
  private double setpoint = 0.0;
  private double error = 0.0;
  private double result = 0.0;
  private double previousInput = Double.NaN;

  public SynchronousPid(double kP, double kI, double kD) {
    cP = kP;
    cI = kI;
    cD = kD;
  }

  public String getType() {
    return "PIDController";
  }

  public double calculate(double input) {
    previousInput = input;
    error = setpoint - input;
    if (continuous) {
      if (Math.abs(error) > (maxInput - minInput) / 2) {
        if (error > 0) {
          error -= maxInput + minInput;
        } else {
          error += maxInput - minInput;
        }
      }
    }

    if ((error * cP < maxOutput) && (error * cP > minOutput)) {
      totalError += error;
    } else {
      totalError = 0;
    }

    result = (cP * error + cI * totalError + cD * (error - previousError));
    previousError = error;

    if (result > maxOutput) {
      result = maxOutput;
    } else if (result < minOutput) {
      result = minOutput;
    }
    return result;
  }

  public void setPid(double p, double i, double d) {
    this.cP = p;
    this.cI = i;
    this.cD = d;
  }

  public double getP() {
    return cP;
  }

  public double getI() {
    return cI;
  }

  public double getD() {
    return cD;
  }

  public double getCurrent() {
    return result;
  }

  public void setContinuous(boolean continuous) {
    this.continuous = continuous;
  }

  public void setContinuous() {
    this.setContinuous(true);
  }

  public void setInputRange(double minimumInput, double maximumInput) {
    if (minimumInput > maximumInput) {
      throw new BoundaryException("Lower bound is greater than upper bound");
    }
    this.minInput = minimumInput;
    this.maxInput = maximumInput;
    setSetpoint(setpoint);
  }

  public void setOutputRange(double minimumOutput, double maximumOutput) {
    if (minimumOutput > maximumOutput) {
      throw new BoundaryException("Lower bound is greater than upper bound");
    }
    this.minOutput = minimumOutput;
    this.maxOutput = maximumOutput;
  }

  public void setSetpoint(double setpoint) {
    if (maxInput > minInput) {
      if (setpoint > maxInput) {
        this.setpoint = maxInput;
      } else if (setpoint < minInput) {
        this.setpoint = minInput;
      } else {
        this.setpoint = setpoint;
      }
    } else {
      this.setpoint = setpoint;
    }
  }

  public boolean onTarget(double tolerance) {
    return previousInput != Double.NaN && Math.abs(previousInput - setpoint) < tolerance;
  }

  public double getSetpoint() {
    return setpoint;
  }

  public double getError() {
    return error;
  }

  public void reset() {
    previousInput = Double.NaN;
    previousError = 0;
    totalError = 0;
    result = 0;
    setpoint = 0;
  }

  public void resetIntegrator() {
    totalError = 0;
  }

  public String getState() {
    String lState = "";

    lState += "Proportional Constant: " + cP + "\n";
    lState += "Integral Constant: " + cI + "\n";
    lState += "Derivative Constant: " + cD + "\n";

    return lState;
  }

}
