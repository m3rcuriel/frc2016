package com.mvrt.lib.components.oi;

/**
 * An abstract representation of an axis which can point in a direction.
 *
 * @author Lee Mracek
 */
public interface DirectionalAxis {

  /**
   * Get the direction as an int.
   *
   * @return the int value of the direction
   */
  public int getDirection();
}
