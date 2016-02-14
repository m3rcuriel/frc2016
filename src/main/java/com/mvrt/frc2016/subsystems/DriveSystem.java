package com.mvrt.frc2016.subsystems;

import com.mvrt.frc2016.Constants;
import com.mvrt.frc2016.RobotManager;
import com.mvrt.frc2016.subsystems.controllers.ConstantPidController;
import com.mvrt.lib.api.Runnable;
import com.mvrt.lib.api.Subsystem;
import com.mvrt.lib.components.DriveTrain;
import com.mvrt.lib.components.Gyroscope;
import com.mvrt.lib.components.Motor;
import com.mvrt.lib.components.SimpleAccumulatedSensor;
import com.mvrt.lib.control.DriveController;
import com.mvrt.lib.control.controllers.drive.DriveStraightController;
import com.mvrt.lib.control.misc.DriveSignal;
import com.mvrt.lib.control.misc.DriveState;
import com.mvrt.lib.control.misc.PidConstants;

import java.util.concurrent.TimeUnit;

/**
 * This year's Robot drive train subsystem.
 *
 * @author Lee Mracek
 */
public class DriveSystem extends Subsystem implements DriveTrain, Runnable {

  private final Motor leftMotor, rightMotor;
  private final SimpleAccumulatedSensor leftEncoder, rightEncoder;
  private final Gyroscope gyroscope;

  private DriveController controller = null;
  private ConstantPidController cscController = null;

  private DriveState driveState = new DriveState(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);

  /**
   * Construct the frc2016 DriveSystem based on relevant components.
   *
   * @param name         the name of the Subsystem
   * @param leftMotor    the left motor
   * @param rightMotor   the right motor
   * @param leftEncoder  the encoder for the left side of the robot
   * @param rightEncoder the encoder for the right side of the robot
   * @param gyroscope    the gyroscope representing the robot heading and angular velocity.
   */
  public DriveSystem(String name, Motor leftMotor, Motor rightMotor,
      SimpleAccumulatedSensor leftEncoder, SimpleAccumulatedSensor rightEncoder,
      Gyroscope gyroscope) {
    super(name);
    this.leftMotor = leftMotor;
    this.rightMotor = rightMotor;
    this.leftEncoder = leftEncoder;
    this.rightEncoder = rightEncoder;
    this.gyroscope = gyroscope;
  }

  /**
   * Set the DriveSystem to a specific output {@link DriveSignal} and eliminate the current
   * {@link DriveController}.
   *
   * @param signal the desired drive output
   */
  public void setOpenLoop(DriveSignal signal) {
    controller = null;
    this.drive(signal);
  }

  /**
   * Reset the distance readings of the DriveSystem.
   */
  public void reset() {
    leftEncoder.zero();
    rightEncoder.zero();
  }

  /**
   * Drive the robot on a specific {@link DriveSignal}.
   *
   * @param signal the desired motor output
   */
  @Override
  public void drive(DriveSignal signal) {
    this.leftMotor.setSpeed(signal.leftMotor);
    this.rightMotor.setSpeed(signal.rightMotor);
  }

  @Override
  public void run(long time) {
    if (controller == null) {
      return;
    }
    drive(controller.update(getDriveState()));
  }

  public void setConstantSpeed(double speed) {
    if (!(controller instanceof ConstantPidController)) {
      controller = new ConstantPidController(
          new PidConstants(Constants.kConstantDriveKp, Constants.kConstantDriveKi, Constants.kConstantDriveKd),
          Constants.kConstantDriveAcceptableBitwiseError);
      ((ConstantPidController) controller).setGoal(speed);
    } else {
      ((ConstantPidController) controller).setGoal(speed);
    }
  }

  public void setDistanceSetpoint(double distance) {
    setDistanceSetpoint(distance, Constants.kDriveMaxVelocity);
  }

  public void setDistanceSetpoint(double distance, double velocity) {
    if (velocity < 0) {
      throw new IllegalArgumentException("Velocity may not be negative");
    }
    double realVelocity = Math.min(Constants.kDriveMaxVelocity, Math.max(velocity, 0));
    controller = new DriveStraightController(getStateToContinueFrom(), distance,
        ((double) (RobotManager.SLOW_CONTROLLERS_MILLISECONDS)) / 1000D,
        realVelocity, Constants.kDriveMaxAcceleration,
        new PidConstants(Constants.kDriveDistanceKp, Constants.kDriveDistanceKi,
            Constants.kDriveDistanceKd), Constants.kDriveDistanceKv, Constants.kDriveDistanceKa,
        Constants.kDriveOnTargetError,
        new PidConstants(Constants.kDriveStraightKp, Constants.kDriveStraightKi,
            Constants.kDriveStraightKd));
  }

  /**
   * Retrieve the current {@link DriveController}.
   * <p>
   * May be null.
   *
   * @return the currently-used {@link DriveController}
   */
  public DriveController getController() {
    return controller;
  }

  /**
   * Retrieve the current {@link DriveState} of the robot using encoder values and gyroscope data.
   *
   * @return the current {@link DriveState}
   */
  public DriveState getDriveState() {
    driveState.reset(leftEncoder.getPosition(), rightEncoder.getPosition(), leftEncoder.getRate(),
        rightEncoder.getRate(), gyroscope.getHeading(), gyroscope.getRate());
    return driveState;
  }

  private DriveState getStateToContinueFrom() {
    if (controller == null) {
      return getDriveState();
    } else if (controller.isOnTarget()) {
      return controller.getCurrentState();
    } else {
      return getDriveState();
    }
  }

  public boolean controllerOnTarget() {
    if (controller != null) {
      return controller.isOnTarget();
    }
    return false;
  }

}
