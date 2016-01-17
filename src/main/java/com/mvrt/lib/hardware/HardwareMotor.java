package com.mvrt.lib.hardware;

import com.mvrt.lib.components.Motor;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.SpeedController;

import java.util.function.DoubleFunction;

/**
 * A hardware representation of a basic motor allowing the user to manipulate and retrieve the
 * speed.
 *
 * @author Lee Mracek
 */
final class HardwareMotor implements Motor {
  private final SpeedController controller;
  private final DoubleFunction<Double> speedValidator;

  /**
   * Construct a new hardware representation of a motor with the following {@link SpeedController}
   * and the given speed validator function.
   *
   * @param controller     the physical {@link SpeedController}
   * @param speedValidator the function which validates speed input
   */
  HardwareMotor(SpeedController controller, DoubleFunction<Double> speedValidator) {
    this.controller = controller;
    this.speedValidator = speedValidator;
  }

  /**
   * Set the physical motor's speed to a given value.
   *
   * @param speed the new speed of the motor
   * @return this HardwareMotor for method chaining
   */
  @Override
  public HardwareMotor setSpeed(double speed) {
    controller.set(speedValidator.apply(speed));
    return this;
  }

  /**
   * Get the physical motor's speed.
   *
   * @return the speed of the motor
   */
  @Override
  public double getSpeed() {
    return controller.get();
  }

  /**
   * Retrieve the speed as a raw PWM cycle.
   *
   * @return the short representing the raw value of the motor
   */
  public short getSpeedAsShort() {
    return (short) ((PWM) controller).getRaw();
  }
}
