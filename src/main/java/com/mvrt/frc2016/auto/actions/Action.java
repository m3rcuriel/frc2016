package com.mvrt.frc2016.auto.actions;

import com.mvrt.frc2016.subsystems.DriveSystem;
import com.mvrt.frc2016.system.Robot;

/**
 * Created by lee on 1/25/16.
 */
public abstract class Action {
  // Pass in all subsystems here
  protected DriveSystem driveSystem;

  public Action(Robot robot) {
    driveSystem = robot.driveSystem;
  }

  public abstract boolean isFinished();

  public abstract void update();

  public abstract void done();

  public abstract void start();
}
