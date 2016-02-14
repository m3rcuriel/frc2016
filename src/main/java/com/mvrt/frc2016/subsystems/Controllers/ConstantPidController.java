package com.mvrt.frc2016.subsystems.controllers;

import com.mvrt.lib.control.misc.PidConstants;
import com.mvrt.lib.control.misc.SynchronousPid;
import com.mvrt.lib.util.Values;

/**
 * Controller for Driving at a constant speed using PID.
 *
 * @author Siddharth Gollapudi on 2/13/16.
 */
public class ConstantPidController extends ConstantSpeedController {

  private final int acceptableBitwiseError;

  private SynchronousPid pid;

  public boolean target = false;

  public ConstantPidController(PidConstants constants, int acceptableBitwiseError) {
    this.acceptableBitwiseError = acceptableBitwiseError;
    this.pid = new SynchronousPid(constants.kP, constants.kI, constants.kD);
    pid.setOutputRange(-1.0, 1.0);
  }

  @Override
  public void setGoal(double velocity) {
    this.goal = velocity;
    pid.setSetpoint(velocity);
  }

  @Override
  public void update(double velocity) {
    powerOutput = pid.calculate(velocity);

    target = Values.fuzzyCompare(velocity, goal, acceptableBitwiseError) == 0;
  }

  @Override
  public void reset() {
    powerOutput = 0;
    goal = 1;
  }

  @Override
  public boolean isOnTarget() {
    return target;
  }
}
