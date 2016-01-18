package com.mvrt.lib.components;

import java.util.function.DoubleSupplier;

/**
 * An interface representation of a sensor which reads the derivative and accumulates the value.
 * Example: read dx/dt, accumulate to get x
 *
 * @author Lee Mracek
 */
public interface SimpleAccumulatedSensor {

  double getPosition();

  double getRate();

  SimpleAccumulatedSensor zero();

  public static SimpleAccumulatedSensor create(DoubleSupplier positionSupplier, DoubleSupplier rateSupplier) {
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
