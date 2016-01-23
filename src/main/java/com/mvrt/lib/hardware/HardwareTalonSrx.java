package com.mvrt.lib.hardware;

import com.mvrt.lib.components.Switch;
import com.mvrt.lib.components.TalonSrx;
import com.mvrt.lib.util.DoubleMatrix;
import edu.wpi.first.wpilibj.CANTalon;

import java.util.function.DoubleFunction;

/**
 * A hardware representation of a TalonSRX allowing the user to manipulate and retrieve the
 * speed.
 * @author Ishan
 */
public class HardwareTalonSrx implements TalonSrx {

  protected final CANTalon talon;
  protected final DoubleFunction<Double> limiter;

  protected final Switch forwardLimitSwitch;
  protected final Switch reverseLimitSwitch;
  protected final Faults instantaneousFaults;
  protected final Faults stickyFaults;

  public HardwareTalonSrx(CANTalon talon, DoubleFunction<Double> limiter) {
    this.talon = talon;
    this.limiter = limiter;

    this.forwardLimitSwitch = talon::isRevLimitSwitchClosed;
    this.reverseLimitSwitch = talon::isFwdLimitSwitchClosed;

    this.instantaneousFaults = new Faults() {
      @Override
      public Switch forwardLimitSwitch() {
        return () -> talon.getFaultForLim() != 0;
      }

      @Override
      public Switch reverseLimitSwitch() {
        return () -> talon.getFaultRevLim() != 0;
      }

      @Override
      public Switch forwardSoftLimit() {
        return () -> talon.getFaultForSoftLim() != 0;
      }

      @Override
      public Switch reverseSoftLimit() {
        return () -> talon.getFaultRevSoftLim() != 0;
      }

      @Override
      public Switch hardwareFailure() {
        return () -> talon.getFaultHardwareFailure() != 0;
      }

      @Override
      public Switch overTemperature() {
        return () -> talon.getFaultOverTemp() != 0;
      }

      @Override
      public Switch underVoltage() {
        return () -> talon.getFaultUnderVoltage() != 0;
      }
    };

    this.stickyFaults = new Faults() {
      @Override
      public Switch forwardLimitSwitch() {
        return () -> talon.getStickyFaultForLim() != 0;
      }

      @Override
      public Switch reverseLimitSwitch() {
        return () -> talon.getStickyFaultRevLim() != 0;
      }

      @Override
      public Switch forwardSoftLimit() {
        return () -> talon.getStickyFaultForSoftLim() != 0;
      }

      @Override
      public Switch reverseSoftLimit() {
        return () -> talon.getStickyFaultRevSoftLim() != 0;
      }

      @Override
      public Switch hardwareFailure() {
        return () -> talon.getFaultHardwareFailure() != 0; // no sticky version!
      }

      @Override
      public Switch overTemperature() {
        return () -> talon.getStickyFaultOverTemp() != 0;
      }

      @Override
      public Switch underVoltage() {
        return () -> talon.getStickyFaultUnderVoltage() != 0;
      }
    };
  }

  @Override
  public double getSpeed() {
    talon.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    return talon.get();
  }

  @Override
  public TalonSrx setSpeed(double speed) {
    talon.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    talon.set(speed);
    return this;
  }

  @Override
  public Switch getForwardLimitSwitch() {
    return forwardLimitSwitch;
  }

  @Override
  public Switch getReverseLimitSwitch() {
    return reverseLimitSwitch;
  }

  @Override
  public int getDeviceId() {
    return talon.getDeviceID();
  }

  @Override
  public TalonSrx setFeedbackDevice(CANTalon.FeedbackDevice device) {
    talon.setFeedbackDevice(device);
    return this;
  }

  @Override
  public TalonSrx reverseSensor(boolean flip) {
    talon.reverseSensor(flip);
    return this;
  }

  @Override
  public TalonSrx setForwardSoftLimit(int forwardLimit) {
    talon.setForwardSoftLimit(forwardLimit);
    return this;
  }

  @Override
  public TalonSrx enableForwardSoftLimit(boolean enable) {
    talon.enableForwardSoftLimit(enable);
    return this;
  }

  @Override
  public TalonSrx setReverseSoftLimit(int reverseLimit) {
    talon.setReverseSoftLimit(reverseLimit);
    return this;
  }

  @Override
  public TalonSrx enableReverseSoftLimit(boolean enable) {
    talon.enableReverseSoftLimit(enable);
    return this;
  }

  @Override
  public TalonSrx enableLimitSwitch(boolean forward, boolean reverse) {
    talon.enableLimitSwitch(forward, reverse);
    return this;
  }

  @Override
  public TalonSrx setForwardLimitSwitchNormallyOpen(boolean normallyOpen) {
    talon.ConfigFwdLimitSwitchNormallyOpen(normallyOpen);
    return this;
  }

  @Override
  public TalonSrx setReverseLimitSwitchNormallyOpen(boolean normallyOpen) {
    talon.ConfigRevLimitSwitchNormallyOpen(normallyOpen);
    return this;
  }

  @Override
  public TalonSrx enableBrakeMode(boolean brake) {
    talon.enableBrakeMode(brake);
    return this;
  }

  @Override
  public TalonSrx setVoltageRampRate(double rampRate) {
    talon.setVoltageRampRate(rampRate);
    return this;
  }

  @Override
  public Faults faults() {
    return instantaneousFaults;
  }

  @Override
  public Faults stickyFaults() {
    return stickyFaults;
  }

  @Override
  public TalonSrx clearStickyFaults() {
    talon.clearStickyFaults();
    return this;
  }

  @Override
  public double pidGet() {
    return talon.pidGet();
  }

  @Override
  public boolean isSafetyEnabled() {
    return talon.isSafetyEnabled();
  }

  @Override
  public TalonSrx setSafetyEnabled(boolean enabled) {
    talon.setSafetyEnabled(enabled);
    return this;
  }

  @Override
  public boolean isAlive() {
    return talon.isAlive();
  }
}
