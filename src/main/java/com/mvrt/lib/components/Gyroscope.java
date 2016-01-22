package com.mvrt.lib.components;

/**
 * Created by lee on 1/21/16.
 */
public interface Gyroscope {
  /**
   * Get the current angular rate as measured by the gyroscope.
   *
   * @return the angular rate in degrees / sec
   */
  double getRate();

  /**
   * Get the current measured angle as integrated from gyroscope data.
   *
   * @return the angle in degrees (-180 to 180)
   */
  double getAngle();

  /**
   * Get the heading of the robot, or the angle on a range of 0 to 360.
   *
   * @return the current heading in degrees
   */
  default double getHeading() {
    return getAngle() % 360;
  }

  /**
   * Change the zero point of the gyroscope to the current sensor value.
   */
  void zero();

  /**
   * Get the raw angle from the gyroscope.
   *
   * @return the raw angle
   */
  double getRawAngle();

  /**
   * Get the heading based on the raw angle.
   *
   * @return the raw angle
   */
  default double getRawHeading() {
    return getRawAngle() % 360;
  }
}
