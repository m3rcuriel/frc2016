package com.mvrt.lib.components;

/**
 * Created by lee on 1/21/16.
 */
public interface Gyroscope {
  double getRate();

  double getAngle();

  default double getHeading() {
    return getAngle() % 360;
  }

  void reset();

  double getRawAngle();

  default double getRawHeading() {
    return getHeading() % 360;
  }
}
