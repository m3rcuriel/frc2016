package com.mvrt.lib.hardware;

import com.mvrt.lib.components.Motor;
import com.mvrt.lib.components.Switch;
import edu.wpi.first.wpilibj.CANTalon;

import java.util.function.DoubleFunction;

/**
 * Created by Samster on 1/18/2016.
 */
public class HardwareTalonSRX  implements Motor {

  private final CANTalon talon;
  private final DoubleFunction<Double> speedValidator;

  protected final Switch forwardLimitSwitch;
  protected final Switch reverseLimitSwitch;

  public HardwareTalonSRX(CANTalon talon, DoubleFunction<Double> speedValidator){
    this.talon = talon;
    this.speedValidator = speedValidator;
    this.forwardLimitSwitch = talon::isRevLimitSwitchClosed;
    this.reverseLimitSwitch = talon::isFwdLimitSwitchClosed;
  }

  @Override
  public double getSpeed() {
    talon.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    return talon.get();
  }

  @Override
  public Motor setSpeed(double speed) {
    talon.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    talon.set(speedValidator.apply(speed));
    return this;
  }

  public Switch getForwardLimitSwitch(){
    return forwardLimitSwitch;
  }

  public Switch getReverseLimitSwitch(){
    return reverseLimitSwitch;
  }

  public Motor setForwardSoftLimit(int forwardLimit) {
    talon.setForwardSoftLimit(forwardLimit);
    return this;
  }

  public Motor enableForwardSoftLimit(boolean enable) {
    talon.enableForwardSoftLimit(enable);
    return this;
  }

  public Motor setReverseSoftLimit(int reverseLimit) {
    talon.setReverseSoftLimit(reverseLimit);
    return this;
  }

  public Motor enableReverseSoftLimit(boolean enable) {
    talon.enableReverseSoftLimit(enable);
    return this;
  }

  public Motor enableLimitSwitch(boolean forward, boolean reverse) {
    talon.enableLimitSwitch(forward, reverse);
    return this;
  }

  public Motor enableBrakeMode(boolean brake) {
    talon.enableBrakeMode(brake);
    return this;
  }

  public boolean isSafetyEnabled() {
    return talon.isSafetyEnabled();
  }

  public Motor setSafetyEnabled(boolean enabled) {
    talon.setSafetyEnabled(enabled);
    return this;
  }

  public boolean isAlive() {
    return talon.isAlive();
  }

}
