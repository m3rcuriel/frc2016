package com.mvrt.lib.components;

/**
 * Abstract Representation of a sensor measuring temperature.
 * @author Siddharth Gollapudi
 */
public interface TemperatureSensor {

  /**
   * Retrieve the current temperature in degrees Celsius.
   * @return the temperature
   */
  public double getTemperatureInCelsius();

  /**
   * Retrieve the current temperature in degrees Fahrenheit.
   * @return the temperature
   */
  public default double getTemperatureInFahrenheit() {
    return getTemperatureInCelsius() * 9.0 / 5.0 + 32.0;
  }
}
