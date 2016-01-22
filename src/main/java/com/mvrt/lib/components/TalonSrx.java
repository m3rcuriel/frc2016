package com.mvrt.lib.components;

import edu.wpi.first.wpilibj.CANTalon;

/**
 * Talon speed controller with Faults and Limit Switches.
 * @author Bubby
 */
public interface TalonSrx extends LimitedMotor{

  TalonSrx setSpeed(double speed);

  int getDeviceId();

  TalonSrx setFeedbackDevice(CANTalon.FeedbackDevice device);

  TalonSrx reverseSensor(boolean flip);

  TalonSrx setForwardSoftLimit(int forwardLimit);

  TalonSrx enableForwardSoftLimit(boolean enable);

  TalonSrx setReverseSoftLimit(int reverseLimit);

  TalonSrx enableReverseSoftLimit(boolean enable);

  TalonSrx enableLimitSwitch(boolean forward, boolean reverse);

  TalonSrx setForwardLimitSwitchNormallyOpen(boolean normallyOpen);

  TalonSrx setReverseLimitSwitchNormallyOpen(boolean normallyOpen);

  TalonSrx enableBrakeMode(boolean brake);

  TalonSrx setVoltageRampRate(double rampRate);

  Faults faults();

  Faults stickyFaults();

  TalonSrx clearStickyFaults();

  boolean isSafetyEnabled();

  TalonSrx setSafetyEnabled(boolean enabled);

  boolean isAlive();

  static interface Faults {

    Switch overTemperature();

    Switch underVoltage();

    Switch forwardLimitSwitch();

    Switch reverseLimitSwitch();

    Switch forwardSoftLimit();

    Switch reverseSoftLimit();

    Switch hardwareFailure();
  }

}
