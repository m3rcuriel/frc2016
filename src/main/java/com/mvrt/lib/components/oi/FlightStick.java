package com.mvrt.lib.components.oi;

import com.mvrt.lib.components.Switch;

import java.util.function.IntFunction;

/**
 * An abstract representation of a flightstick style {@link InputDevice}.
 *
 * @author Lee Mracek
 */
public interface FlightStick extends InputDevice {
  /**
   * Get the pitch of the controller.
   *
   * @return the pitch of the controller
   */
  ContinuousRange getPitch();

  /**
   * Gets the yaw of the controller.
   *
   * @return the yaw of the controller
   */
  ContinuousRange getYaw();

  /**
   * Gets the roll of the controller.
   *
   * @return the roll of the controller
   */
  ContinuousRange getRoll();

  /**
   * Gets the throttle of the controller.
   *
   * @return the throttle of the controller
   */
  ContinuousRange getThrottle();

  /**
   * Gets a {@link Switch} representing the trigger button.
   *
   * @return the {@link Switch}
   */
  Switch getTrigger();

  /**
   * Gets a {@link Switch} representing the thumb button.
   *
   * @return a {@link Switch} representing the thumb button
   */
  Switch getThumb();

  /**
   * Create a new FlightStick implementation based on a set of functions.
   *
   * @param axisToValue          the {@link IntFunction} which converts axis index to value
   * @param buttonNumberToSwitch the {@link IntFunction} which converts button index to
   *                             boolean
   * @param padToValue           the {@link IntFunction} which converts pad index to value
   * @param pitch                the {@link ContinuousRange} representing the pitch of the
   *                             controller
   * @param yaw                  the {@link ContinuousRange} representing the yaw of the controller
   * @param roll                 the {@link ContinuousRange} representing the roll of the controller
   * @param throttle             the {@link Switch} representing the throttle of the controller
   * @param trigger              the {@link Switch} representing the  the trigger of the controller
   * @param thumb                the {@link Switch} representing the thumb button of the controller
   * @return the newly created FlightStick
   */
  static FlightStick create(IntFunction<Double> axisToValue,
      IntFunction<Boolean> buttonNumberToSwitch, IntFunction<Integer> padToValue,
      ContinuousRange pitch, ContinuousRange yaw, ContinuousRange roll, ContinuousRange throttle,
      Switch trigger, Switch thumb) {
    return new FlightStick() {
      @Override
      public ContinuousRange getPitch() {
        return pitch;
      }

      @Override
      public ContinuousRange getYaw() {
        return yaw;
      }

      @Override
      public ContinuousRange getRoll() {
        return roll;
      }

      @Override
      public ContinuousRange getThrottle() {
        return throttle;
      }

      @Override
      public Switch getTrigger() {
        return trigger;
      }

      @Override
      public Switch getThumb() {
        return thumb;
      }

      @Override
      public ContinuousRange getAxis(int axis) {
        return () -> axisToValue.apply(axis);
      }

      @Override
      public Switch getButton(int button) {
        return () -> buttonNumberToSwitch.apply(button);
      }

      public DirectionalAxis getDPad(int pad) {
        return () -> padToValue.apply(pad);
      }
    };
  }
}
