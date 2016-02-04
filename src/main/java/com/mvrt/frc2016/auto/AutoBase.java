package com.mvrt.frc2016.auto;

import com.mvrt.frc2016.RobotManager;
import com.mvrt.frc2016.auto.actions.Action;
import com.mvrt.lib.util.Metronome;

import java.util.concurrent.TimeUnit;

/**
 * An abstract class which represents a single autonomous mode. This class contains methods for
 * running the autonomous at a given period, as well as handling the execution flow.
 *
 * @author Lee Mracek
 */
public abstract class AutoBase {
  protected double updateRate = 1.0 / 50.0;
  protected boolean active = false;

  protected abstract void routine() throws AutoEndedException;

  /**
   * Method called before the autonomous mode executes.
   */
  public abstract void setup();

  protected Metronome metronome = Metronome
      .metronome((long) (updateRate * 1000), TimeUnit.MILLISECONDS,
          RobotManager::getRobotTimeMillis);

  /**
   * Method called periodically to execute the autonomous mode.
   */
  public void run() {
    active = true;
    try {
      routine();
    } catch (AutoEndedException e) {
      System.out.println("Auto mode done, ended early");
      return;
    }
    System.out.println("Auto mode done");
  }

  /**
   * Stop the autonomous mode.
   */
  public void stop() {
    active = false;
  }

  /**
   * Determine if the autonomous is currently running.
   *
   * @return true if the autonomous is running
   */
  public boolean isActive() {
    return active;
  }

  /**
   * Utility method which throws an {@link AutoEndedException} if the autonomous routine is no
   * longer running.
   *
   * @return true if the autonomous is running
   * @throws AutoEndedException if the autonomous is no longer running
   */
  public boolean throwIfInactive() throws AutoEndedException {
    if (!isActive()) {
      throw new AutoEndedException();
    }
    // could just return true but that's lame. If this causes a bug I'm sorry.
    return isActive();
  }

  /**
   * Runs an action as part of the autonomous mode.
   *
   * @param action the action to execute
   * @throws AutoEndedException if the action exits early
   */
  public void runAction(Action action) throws AutoEndedException {
    throwIfInactive();
    action.start();
    while (throwIfInactive() && !action.isFinished()) {
      action.update();
      metronome.pause();
    }

    action.done();
  }

}
