package com.mvrt.frc2016.subsystems.controllers;

import com.mvrt.frc2016.Constants;
import com.mvrt.lib.components.Motor;
import com.mvrt.lib.control.DriveController;
import com.mvrt.lib.control.misc.DriveSignal;
import com.mvrt.lib.control.misc.DriveState;
import com.mvrt.lib.control.misc.PidConstants;
import com.mvrt.lib.control.misc.SynchronousPid;
import com.mvrt.lib.util.Values;

/**
 * Controller for Driving at a constant speed using PID.
 *
 * @author Siddharth Gollapudi on 2/13/16.
 */
public class ConstantPidController extends DriveController {

  private final int acceptableBitwiseError;

  protected double goal;
  protected double lSpeed;
  protected double rSpeed;

  private SynchronousPid pid;
  private DriveState currentState = null;

  public boolean targetRight = false;
  public boolean targetLeft = false;

  public ConstantPidController(PidConstants constants, int acceptableBitwiseError) {
    this.acceptableBitwiseError = acceptableBitwiseError;
    this.pid = new SynchronousPid(constants.kP, constants.kI, constants.kD);
    pid.setOutputRange(-1.0, 1.0);
  }

  public void setGoal(double velocity) {
    this.goal = velocity;
    pid.setSetpoint(goal);
  }

  @Override
  public DriveSignal update(DriveState currentState) {
    lSpeed = pid.calculate(currentState.getLeftVelocity());
    rSpeed = pid.calculate(currentState.getRightVelocity());

    targetRight = Values.fuzzyCompare(rSpeed, goal, acceptableBitwiseError) == 0;
    targetLeft = Values.fuzzyCompare(lSpeed, goal, acceptableBitwiseError) == 0;

    return new DriveSignal((double) Constants.kDriveLeftFrontId,
        (double) Constants.kDriveRightFrontId);
  }

  @Override
  public DriveState getCurrentState() {
    return new DriveState(currentState.getLeftDistance(), currentState.getRightDistance(),
        currentState.getLeftVelocity(), currentState.getRightVelocity(), currentState.getHeading(),
        currentState.getHeadingVelocity());
  }

  @Override
  public void reset() {
    lSpeed = 0;
    rSpeed = 0;
    goal = 0;
  }

  @Override
  public boolean isOnTarget() {
    return targetLeft && targetRight;
  }
}
