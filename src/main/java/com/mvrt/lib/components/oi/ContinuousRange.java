package com.mvrt.lib.components.oi;

import java.util.function.DoubleFunction;
import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;

/**
 * Class represents a value on a continuous range which can be scaled, mapped,
 * or inverted.
 *
 * @author Lee Mracek
 */
public interface ContinuousRange {
  /**
   * Read the current value of the ContinuousRange.
   *
   * @return the current value of the ContinuousRange
   */
  public double read();

  /**
   * Invert the ContinuousRange.
   *
   * @return the newly inverted ContinuousRange
   */
  default ContinuousRange invert() {
    return () -> this.read() * -1.0;
  }

  /**
   * Scale the ContinuousRange using some scalar value.
   *
   * @param scale the value to scale by
   * @return the newly scaled ContinuousRange
   */
  default ContinuousRange scale(double scale) {
    return () -> this.read() * scale;
  }

  /**
   * Scale the ContinuousRange using some {@link DoubleSupplier}.
   *
   * @param scale the {@link DoubleSupplier} supplying scaling values
   * @return the newly scaled ContinuousRange
   */
  default ContinuousRange scale(DoubleSupplier scale) {
    return () -> this.read() * scale.getAsDouble();
  }

  /**
   * Map the ContinuousRange to some {@link DoubleFunction}.
   *
   * @param mapper the {@link DoubleFunction} which maps ths ContinuousRange
   * @return the newly mapped ContinuousRange
   */
  default ContinuousRange map(DoubleFunction<Double> mapper) {
    return () -> mapper.apply(this.read());
  }

  /**
   * Scales the ContinuousRange as an integer rather than as a double.
   *
   * @param scale the scalar to multiply each int by
   * @return the IntSupplier representing the scaled ContinuousRange
   */
  default IntSupplier scaleAsInt(double scale) {
    return () -> (int) (this.read() * scale);
  }
}
