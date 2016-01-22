package com.mvrt.lib.components;

/**
 * Abstract representation of a sensor that measures voltage.
 *
 * @author Siddharth Gollapudi
 */
public interface VoltageSensor {

  /**
   * Retrieve the voltage.
   *
   * @return the voltage
   */
  public double getVoltage();
}
