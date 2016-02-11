package com.mvrt.frc2016.subsystems.controllers;

import com.mvrt.lib.control.misc.PidConstants;
import com.mvrt.lib.control.misc.SynchronousPid;
import com.mvrt.lib.util.Values;

/**
 * A controller for the flywheel using simple PID.
 *
 * @author Lee Mracek
 */
public class FlywheelPidController extends FlywheelController {

  private final int acceptableBitwiseError;

  private SynchronousPid pid;

  public boolean onTarget = false;

  public FlywheelPidController(PidConstants pidConstants, int acceptableBitwiseError) {
    this.pid = new SynchronousPid(pidConstants.kP, pidConstants.kI, pidConstants.kD);
    this.acceptableBitwiseError = acceptableBitwiseError;
    pid.setOutputRange(-1.0, 1.0);
  }

  @Override
  public void setGoal(double velocity) {
    this.goal = velocity;
    pid.setSetpoint(velocity);
  }

  @Override
  public void update(double position, double velocity) {
    powerOutput = pid.calculate(velocity);

    onTarget = Values.fuzzyCompare(velocity, goal, acceptableBitwiseError) == 0;
  }

  @Override
  public void reset() {
    powerOutput = 0;
    goal = 0;
  }

  @Override
  public boolean isOnTarget() {
    return onTarget;
  }
}
