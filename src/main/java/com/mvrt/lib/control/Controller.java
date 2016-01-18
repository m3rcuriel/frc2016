package com.mvrt.lib.control;

/**
 * An abstract representation of a controller.
 *
 * @author Lee Mracek
 */
public abstract class Controller {
  /**
   * Reflects the state of the Controller.
   */
  protected boolean enabled = false;

  /**
   * Reset the controller.
   */
  public abstract void reset();

  /**
   * Determine if the controller is on target.
   *
   * @return true if the controller is on target
   */
  public abstract boolean isOnTarget();
}
