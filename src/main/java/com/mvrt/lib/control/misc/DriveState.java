package com.mvrt.lib.control.misc;

/**
 * Created by lee on 1/21/16.
 */
public class DriveState {
  private double leftDistance;
  private double rightDistance;
  private double leftVelocity;
  private double rightVelocity;
  private double heading;
  private double headingVelocity;

  public DriveState(double leftDistance, double rightDistance, double leftVelocity,
      double rightVelocity, double heading, double headingVelocity) {
    this.leftDistance = leftDistance;
    this.rightDistance = rightDistance;
    this.leftVelocity = leftVelocity;
    this.rightVelocity = rightVelocity;
    this.heading = heading;
    this.headingVelocity = headingVelocity;
  }

  public void reset(double leftDistance, double rightDistance, double leftVelocity,
      double rightVelocity, double heading, double headingVelocity) {
    this.leftDistance = leftDistance;
    this.rightDistance = rightDistance;
    this.leftVelocity = leftVelocity;
    this.rightVelocity = rightVelocity;
    this.heading = heading;
    this.headingVelocity = headingVelocity;
  }

  public double getLeftDistance() {
    return leftDistance;
  }

  public double getRightDistance() {
    return rightDistance;
  }

  public double getLeftVelocity() {
    return leftVelocity;
  }

  public double getRightVelocity() {
    return rightVelocity;
  }

  public double getHeading() {
    return heading;
  }

  public double getHeadingVelocity() {
    return headingVelocity;
  }
}
