package com.mvrt.frc2016.auto.actions;

import com.mvrt.frc2016.system.Robot;

import java.sql.DriverAction;
import java.util.concurrent.TimeUnit;

/**
 * Created by lee on 1/25/16.
 */
public class WaitForDriveAction extends TimeoutAction {
  public WaitForDriveAction(Robot robot, double timeout) {
    super(robot, (long) timeout * 1000, TimeUnit.MILLISECONDS);
  }

  @Override
  public boolean isFinished() {
    return driveSystem.controllerOnTarget() || super.isFinished();
  }
}
