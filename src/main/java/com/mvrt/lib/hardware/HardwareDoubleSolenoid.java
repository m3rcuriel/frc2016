package com.mvrt.lib.hardware;

import com.mvrt.lib.components.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 * Hardware representation of a double solenoid which allows to get the current
 * state and direction, and retract/extend it.
 *
 * @author Siddharth Gollapudi
 */
public class HardwareDoubleSolenoid implements Solenoid {
  private final DoubleSolenoid solenoid;

  private Direction direction;

  /**
   * Constructs a HardwareDoubleSolenoid around an existing WPILib {@link DoubleSolenoid}.
   *
   * @param solenoid the hardware to wrap
   * @param initialDirection the initial direction of the solenoid (will be checked anyway)
   */
  HardwareDoubleSolenoid(DoubleSolenoid solenoid, Direction initialDirection) {
    if (solenoid == null) {
      throw new IllegalArgumentException("Solenoid must not be null");
    }
    this.solenoid = solenoid;
    if (initialDirection != null) {
      this.direction = initialDirection;
    }
    checkState();
  }

  /**
   * Gets the current state of the solenoid, and set it to direction.
   */
  protected void checkState() {
    if (solenoid.get() == DoubleSolenoid.Value.kForward) {
      direction = Direction.EXTENDING;
    } else if (solenoid.get() == DoubleSolenoid.Value.kReverse) {
      direction = Direction.RETRACTING;
    } else {
      direction = Direction.STOPPED;
    }
  }

  /**
   * Get the direction of this solenoid.
   * @return the {@link com.mvrt.lib.components.Solenoid.Direction} of the solenoid.
   */
  @Override
  public Direction getDirection() {
    checkState();
    return direction;
  }

  /**
   * Extend a solenoid.
   * @return this solenoid for method chaining
   */
  @Override
  public HardwareDoubleSolenoid extend() {
    solenoid.set(DoubleSolenoid.Value.kForward);
    direction = Direction.EXTENDING;
    checkState();
    return this;
  }

  /**
   * Retract a solenoid.
   * @return this solenoid for method chaining
   */
  @Override
  public HardwareDoubleSolenoid retract() {
    solenoid.set(DoubleSolenoid.Value.kReverse);
    direction = Direction.RETRACTING;
    checkState();
    return this;
  }

  /**
   * Return the direction in the form of a string
   * @return direction in form of string.
   */
  @Override
  public String toString() {
    return "direction = " + direction;
  }
}
