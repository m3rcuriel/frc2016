package com.mvrt.frc2016.auto.actions;

import com.mvrt.frc2016.system.Robot;
import com.mvrt.lib.control.misc.DriveState;

import java.util.concurrent.TimeUnit;

/**
 * Created by lee on 1/25/16.
 */
public class WaitForDriveDistanceAction extends TimeoutAction {
  private double distance;
  private boolean positive;

  public WaitForDriveDistanceAction(Robot robot, double distance, double seconds) {
    super(robot, (long) seconds * 1000, TimeUnit.MILLISECONDS);
    this.distance = Math.abs(distance);
    this.positive = Math.signum(distance) == 1;
  }

  @Override
  public boolean isFinished() {
    DriveState state = driveSystem.getDriveState();
    double average = (state.getLeftDistance() + state.getRightDistance()) / 2.0;
    return (positive ? average >= distance : average <= distance) || super.isFinished();
  }
}
