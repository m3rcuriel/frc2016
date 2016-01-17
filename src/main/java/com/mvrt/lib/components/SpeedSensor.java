package com.mvrt.lib.components;

/**
 * An interface representing any abstract device which can retrieve a speed.
 *
 * @author Lee Mracek
 */
public interface SpeedSensor {

  /**
   * Gets the current speed.
   *
   * @return the speed
   */
  public double getSpeed();
}
