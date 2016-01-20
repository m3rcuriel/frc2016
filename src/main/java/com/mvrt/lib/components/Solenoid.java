package com.mvrt.lib.components;

/**
 * Abstract representation of linearly actuated solenoid.
 *
 * @author Siddharth Gollapudi
 */
public interface Solenoid {

  /**
   * Enum representing the possible states of the solenoid.
   */
  static enum Direction {
    EXTENDING, RETRACTING, STOPPED;
  }

  /**
   * Retrieve the current {@link Direction} of the Solenoid.
   * @return the current direction of the solenoid.
   */
  Direction getDirection();

  /**
   * Extend the solenoid.
   * @return this Solenoid for method chaining.
   */
  Solenoid extend();

  /**
   * Retract the solenoid.
   * @return this Solenoid for method chaining.
   */
  Solenoid retract();

  // I pray that these are self-explanatory to whomever is reading this.
  default boolean isExtending() {
    return getDirection() == Direction.EXTENDING;
  }

  default boolean isRetracting() {
    return getDirection() == Direction.RETRACTING;
  }

  default boolean isStopped() {
    return getDirection() == Direction.STOPPED;
  }
}
