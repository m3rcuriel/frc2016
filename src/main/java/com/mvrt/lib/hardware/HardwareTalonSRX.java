package com.mvrt.lib.hardware;

import com.mvrt.lib.components.SpeedController;
import com.mvrt.lib.components.Switch;
import edu.wpi.first.wpilibj.CANTalon;

import java.util.function.DoubleFunction;

/**
 * A hardware representation of a TalonSRX allowing the user to manipulate and retrieve the
 * speed.
 * @author Bubby
 */
public class HardwareTalonSrx implements SpeedController {

  private final CANTalon talon;
  private final DoubleFunction<Double> speedValidator;

  protected final Switch forwardLimitSwitch;
  protected final Switch reverseLimitSwitch;

  /**
   * Construct a new hardware representation of TalonSRX speed controller.
   *
   * @param talon          the physical CANTalon
   * @param speedValidator the function which validates speed input
   */
  public HardwareTalonSrx(CANTalon talon, DoubleFunction<Double> speedValidator) {
    this.talon = talon;
    this.speedValidator = speedValidator;
    this.forwardLimitSwitch = talon::isRevLimitSwitchClosed;
    this.reverseLimitSwitch = talon::isFwdLimitSwitchClosed;
  }

  /**
   * Gets the current speed of the talon.
   *
   * @return current speed of talon
   */
  public double getSpeed() {
    talon.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    return talon.get();
  }

  /**
   * Sets the speed of controller.
   *
   * @param  speed the desired speed
   * @return an instance of this talon
   */
  @Override
  public SpeedController setSpeed(double speed) {
    talon.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    talon.set(speedValidator.apply(speed));
    return this;
  }

  /**
   * Gets the forward limit switch.
   *
   * @return the forward limit switch
   */
  public Switch getForwardLimitSwitch() {
    return forwardLimitSwitch;
  }

  /**
   * Gets the reverse limit switch.
   *
   * @return the reverse limit switch
   */
  public Switch getReverseLimitSwitch() {
    return reverseLimitSwitch;
  }

  /**
   * Sets the soft forward soft limit of the motor.
   *
   * @param forwardLimit forward soft limit
   * @return an instance of this controller
   */
  public SpeedController setForwardSoftLimit(int forwardLimit) {
    talon.setForwardSoftLimit(forwardLimit);
    return this;
  }

  /**
   * Enables or disables the forward soft limit.
   *
   * @param enable enables or disables the forward soft limit
   * @return an instance of this controller
   */
  public SpeedController enableForwardSoftLimit(boolean enable) {
    talon.enableForwardSoftLimit(enable);
    return this;
  }

  /**
   * Sets the reverse soft limit.
   *
   * @param reverseLimit reverse soft limit
   * @return an instance of this controller
   */

  public SpeedController setReverseSoftLimit(int reverseLimit) {
    talon.setReverseSoftLimit(reverseLimit);
    return this;
  }

  /**
   * Enables or disables the reverse soft limit.
   *
   * @param enable boolean to enable or disable the reverse soft limit
   * @return an instance of this controller
   */
  public SpeedController enableReverseSoftLimit(boolean enable) {
    talon.enableReverseSoftLimit(enable);
    return this;
  }

  /**
   * Enables or disables both limit switches.
   *
   * @param forward boolean to enable or disable the forward limit switch
   * @param reverse boolean to enable or disable the reverse limit switch
   * @return an instance of this controller
   */
  public SpeedController enableLimitSwitch(boolean forward, boolean reverse) {
    talon.enableLimitSwitch(forward, reverse);
    return this;
  }

  /**
   * Enables the brake mode of the Talon.
   *
   * @param brake boolean to enable or disable brake mode
   * @return an instance of this controller
   */
  public SpeedController enableBrakeMode(boolean brake) {
    talon.enableBrakeMode(brake);
    return this;
  }

  /**
   * Returns safety status of Talon.
   *
   * @return whether the safety is enabled
   */
  public boolean isSafetyEnabled() {
    return talon.isSafetyEnabled();
  }

  /**
   * Set safety status of Talon.
   *
   * @param enabled New safety status
   * @return an instance of this controller
   */
  public SpeedController setSafetyEnabled(boolean enabled) {
    talon.setSafetyEnabled(enabled);
    return this;
  }

  /**
   * Returns if Talon is in operation.
   *
   * @return whether the talon is alive
   */
  public boolean isAlive() {
    return talon.isAlive();
  }

}
