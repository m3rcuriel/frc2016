package com.mvrt.lib.components;

/**
 * An abstract representation of anything with a set-able speed.
 *
 * @author Lee Mracek
 */
public interface SpeedController {

  /**
   * Set the speed of the SpeedController.
   *
   * @param speed the desired speed
   * @return this SpeedController for method chaining
   */
  public SpeedController setSpeed(double speed);
}
