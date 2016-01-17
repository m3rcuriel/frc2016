package com.mvrt.lib.components.oi;

import com.mvrt.lib.components.Switch;

import java.util.function.IntFunction;

/**
 * An abstract representation of a joystick-like input device.
 *
 * @author Lee Mracek
 */
public interface InputDevice {
  /**
   * Get the value of the specified axis.
   *
   * @param axis the axis index to query
   * @return the {@link ContinuousRange} representing the axis
   */
  ContinuousRange getAxis(int axis);

  /**
   * Get the {@link Switch} representing a specific button.
   *
   * @param button the index of the button
   * @return the {@link Switch} representing the button
   */
  Switch getButton(int button);

  /**
   * Get the {@link DirectionalAxis} representing the DPad.
   *
   * @param pad the index of the pad
   * @return the {@link DirectionalAxis} representing the DPad
   */
  DirectionalAxis getDPad(int pad);

  /**
   * Create a new {@link InputDevice} based on the given problems.
   *
   * @param axisToValue          the IntFunction converting from index to axis
   * @param buttonNumberToSwitch the IntFunction converting from index to button
   * @param padToValue           the IntFunction converting from DPad to value
   * @return the newly created InputDevice representing these functions
   */
  static InputDevice create(IntFunction<Double> axisToValue,
      IntFunction<Boolean> buttonNumberToSwitch, IntFunction<Integer> padToValue) {
    return new InputDevice() {
      @Override
      public ContinuousRange getAxis(int axis) {
        return () -> axisToValue.apply(axis);
      }

      @Override
      public Switch getButton(int button) {
        return () -> buttonNumberToSwitch.apply(button);
      }

      @Override
      public DirectionalAxis getDPad(int pad) {
        return () -> padToValue.apply(pad);
      }
    };
  }
}
