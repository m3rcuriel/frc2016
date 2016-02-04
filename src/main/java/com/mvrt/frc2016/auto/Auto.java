package com.mvrt.frc2016.auto;

import com.mvrt.frc2016.auto.actions.TimeoutAction;
import com.mvrt.frc2016.auto.actions.WaitForDriveAction;
import com.mvrt.frc2016.auto.actions.WaitForDriveDistanceAction;
import com.mvrt.frc2016.system.Robot;

import java.util.concurrent.TimeUnit;

/**
 * Abstract autonomous routine containing utility methods to run each action without having to
 * instantiate them from the routine itself.
 *
 * @author Lee Mracek
 */
public abstract class Auto extends AutoBase {

  protected Robot robot;

  /**
   * Construct a new Auto with the given robot object.
   *
   * @param robot the robot
   */
  public Auto(Robot robot) {
    this.robot = robot;
  }

  /**
   * Run an action which blocks for a set period of time.
   *
   * @param time the timeout of the wait action
   * @param unit the {@link TimeUnit} which the timeout is in
   * @throws AutoEndedException if the action was aborted early
   */
  public void waitTime(long time, TimeUnit unit) throws AutoEndedException {
    runAction(new TimeoutAction(robot, time, unit));
  }

  /**
   * Run an action which blocks until the robot has moved a set distance or timed out.
   *
   * @param distance the distance to wait for
   * @param timeout the time period to allow the command to run
   * @throws AutoEndedException if the action aborts early
   */
  public void waitForDriveDistance(double distance, double timeout) throws AutoEndedException {
    runAction(new WaitForDriveDistanceAction(robot, distance, timeout));
  }

  /**
   * Run an action which blocks until the robot drive controller is done.
   *
   * @param timeout the time period to allow the command to run
   * @throws AutoEndedException if the action aborts early
   */
  public void waitForDriveAction(double timeout) throws AutoEndedException {
    runAction(new WaitForDriveAction(robot, timeout));
  }
}
