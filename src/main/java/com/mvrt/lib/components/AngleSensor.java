package com.mvrt.lib.components;

import java.util.function.DoubleSupplier;

/**
 * Sensor measuring angle.
 *
 * @author Siddharth Gollapudi
 */
public interface AngleSensor extends ReZeroable {


  /**
   * Get current measured angle position.
   * @return current pos in deg
   */
  public double getAngle();


  /**
   * Find difference between current location and target angle w/in a tolerance.
   * @param targetAngle our goal
   * @param tolerance error we can allow
   * @return difference between target and goal, 0 if it is w/in tolerance
   */
  public default double computeAngleChangeTo(double targetAngle, double tolerance) {
    double diff = targetAngle - this.getAngle();
    return Math.abs(diff) <= Math.abs(tolerance) ? 0.0 : diff;
  }

  /**
   * Zero sensor.
   * @return zeroed AngleSensor
   */
  @Override
  public default AngleSensor zero() {
    return this;
  }

  /**
   * Make a new AngleSensor using on a {@link DoubleSupplier}.
   * @param angleSupplier the supplier method for the sensor
   * @return the new AngleSensor
   */
  public static AngleSensor create(DoubleSupplier angleSupplier) {
    return new AngleSensor() {
      private volatile double zero = 0;

      @Override
      public double getAngle() {
        return angleSupplier.getAsDouble() - zero;
      }

      @Override
      public AngleSensor zero() {
        zero = angleSupplier.getAsDouble();
        return this;
      }
    };
  }

  /**
   * Invert a given AngleSensor.
   *
   * @param sensor the sensor to invert
   * @return the inverted sensor
   */
  public static AngleSensor invert(AngleSensor sensor) {
    return new AngleSensor() {
      @Override
      public double getAngle() {
        double angle = sensor.getAngle();
        return angle == 0.0 ? 0.0 : -angle;
      }

      @Override
      public AngleSensor zero() {
        return sensor.zero();
      }
    };
  }


}
