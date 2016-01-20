package com.mvrt.lib.components;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

/**
 * Abstract representation of an object that can switch on/off.
 *
 * @author Siddharth Gollapudi
 */
public interface Relay {

  /**
   * The set of states possible in a Relay.
   */
  static enum State {

    /**
     * Relay asked to switch on, not on yet.
     */
    SWITCHING_ON,
    /**
     * 'Tis on.
     */
    ON,
    /**
     * Relay asked to switch off, not off yet.
     */
    SWITCHING_OFF,
    /**
     * 'Tis off.
     */
    OFF,
    /**
     * Relay has unknown state.
     */
    UNKNOWN;
  }

  /**
   * Retrieve the {@link State} of the Relay.
   * @return the {@link State}
   */
  State state();

  /**
   * Switch the Relay on.
   * @return this Relay for method chaining
   */
  Relay on();

  /**
   * Switch the Relay off.
   * @return this Relay for method chaining
   */
  Relay off();

  /**
   * Check if the Relay is {@link State}{@code .ON}.
   * @return true if the Relay is on
   */
  default boolean isOn() {
    return state() == State.ON;
  }

  /**
   * Check if the Relay is {@link State}{@code .OFF}.
   * @return true if the Relay is off
   */
  default boolean isOff() {
    return state() == State.OFF;
  }

  /**
   * Check if the Relay is {@link State}{@code .SWITCHING_ON}.
   * @return true if the Relay is switching on
   */
  default boolean isSwitchingOn() {
    return state() == State.SWITCHING_ON;
  }

  /**
   * Check if the Relay is {@link State}{@code .SWITCHING_OFF}.
   * @return true if the Relay is switching off
   */
  default boolean isSwitchingOff() {
    return state() == State.SWITCHING_OFF;
  }

  /**
   * Construct a new Relay which switches instantaneously from on to off.
   *
   * @param switcher a Consumer to switch the Relay
   * @param onState the method to read the state
   * @return the constructed Relay
   */
  static Relay instantaneous(Consumer<Boolean> switcher, BooleanSupplier onState) {
    return new Relay() {
      @Override
      public State state() {
        return onState.getAsBoolean() ? State.ON : State.OFF;
      }

      @Override
      public Relay on() {
        switcher.accept(Boolean.TRUE);
        return this;
      }

      @Override
      public Relay off() {
        switcher.accept(Boolean.FALSE);
        return this;
      }
    };
  }

}
