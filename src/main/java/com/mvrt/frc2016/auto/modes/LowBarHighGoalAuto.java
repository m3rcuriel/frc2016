package com.mvrt.frc2016.auto.modes;

import com.mvrt.frc2016.auto.Auto;
import com.mvrt.frc2016.auto.AutoEndedException;
import com.mvrt.frc2016.system.Robot;

import java.util.concurrent.TimeUnit;

/**
 * The autonomous for going under the low bar and scoring in the high goal.
 */
public class LowBarHighGoalAuto extends Auto {

  public LowBarHighGoalAuto(Robot robot) {
    super(robot);
  }

  /**
   * This is literally a giant scripting method.
   *
   * @throws AutoEndedException if the mode exits early
   */
  protected void routine() throws AutoEndedException {

    waitTime(1, TimeUnit.SECONDS);

    robot.driveSystem.setDistanceSetpoint(24);

    waitForDriveDistance(10, 1.0);
  }

  @Override
  public void setup() {

  }
}
