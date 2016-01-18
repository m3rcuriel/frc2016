package com.mvrt.lib.components;

import java.util.function.DoubleSupplier;

/**
 * An interface representation of a sensor which reads the derivative and accumulates the value.
 * Example: read dx/dt, accumulate to get x
 *
 * @author Lee Mracek
 */
public interface SimpleAccumulatedSensor {

  /**
   * Get the current position which has been accumulated.
   *
   * @return the current position
   */
  double getPosition();

  /**
   * Get the current measured rate.
   *
   * @return the current measured rate
   */
  double getRate();

  /**
   * Zero the SimpleAccumulatedSensor.
   *
   * @return this newly zeroed sensor
   */
  SimpleAccumulatedSensor zero();

  public static SimpleAccumulatedSensor create(DoubleSupplier positionSupplier,
      DoubleSupplier rateSupplier) {
    return new SimpleAccumulatedSensor() {
      private volatile double zero = 0;

      @Override
      public double getPosition() {
        return positionSupplier.getAsDouble() - zero;
      }

      @Override
      public double getRate() {
        return rateSupplier.getAsDouble();
      }

      @Override
      public SimpleAccumulatedSensor zero() {
        zero = positionSupplier.getAsDouble();
        return this;
      }
    };
  }

  /**
   * Invert a SimpleAccumulatedSensor.
   *
   * @param sensor the sensor to invert
   * @return the inverted sensor
   */
  static SimpleAccumulatedSensor invert(SimpleAccumulatedSensor sensor) {
    return new SimpleAccumulatedSensor() {
      @Override
      public double getPosition() {
        return sensor.getPosition() == 0.0 ? 0.0 : -sensor.getPosition();
      }

      @Override
      public double getRate() {
        return sensor.getRate() == 0.0 ? 0.0 : -sensor.getRate();
      }

      @Override
      public SimpleAccumulatedSensor zero() {
        return sensor.zero();
      }
    };
  }
}
