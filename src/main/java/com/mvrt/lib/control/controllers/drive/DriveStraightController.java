package com.mvrt.lib.control.controllers.drive;

import com.mvrt.lib.control.DriveController;
import com.mvrt.lib.control.controllers.TrajectoryFollowingPositionController1D;
import com.mvrt.lib.control.misc.DriveSignal;
import com.mvrt.lib.control.misc.DriveState;
import com.mvrt.lib.control.misc.PidConstants;
import com.mvrt.lib.control.misc.SynchronousPid;
import com.mvrt.lib.control.trajectory.TrajectoryFollower1D;

/**
 * @author Lee Mracek
 */
public class DriveStraightController extends DriveController {

  private TrajectoryFollowingPositionController1D distanceController;
  private SynchronousPid turnPid;
  private DriveState setpointRelativeDriveState;

  public DriveStraightController(DriveState priorState, double goalSetpoint, double dt, double
      maxVelocity, double maxAcceleration, PidConstants drive, double kV, double kA,
      double driveOnTargetError, PidConstants turn) {
    TrajectoryFollower1D.TrajectoryConfig config = new TrajectoryFollower1D.TrajectoryConfig();
    config.dt = dt;
    config.maxVelocity = maxVelocity;
    config.maxAcceleration = maxAcceleration;

    distanceController = new TrajectoryFollowingPositionController1D(drive.kP,
        drive.kI, drive.kD, kV, kA, driveOnTargetError, config);

    TrajectoryFollower1D.TrajectorySetpoint initialSetpoint = new TrajectoryFollower1D
        .TrajectorySetpoint();
    initialSetpoint.position = encoderDistance(priorState);
    initialSetpoint.velocity = encoderVelocity(priorState);
    distanceController.setGoal(initialSetpoint, goalSetpoint);

    turnPid = new SynchronousPid(turn.kP, turn.kI, turn.kD);
    turnPid.setSetpoint(priorState.getHeading());
    setpointRelativeDriveState = new DriveState(
        priorState.getLeftDistance(),
        priorState.getRightDistance(),
        0,
        0,
        priorState.getHeading(),
        priorState.getHeadingVelocity());
  }

  @Override
  public DriveSignal update(DriveState currentState) {
    distanceController.update(
        encoderDistance(currentState), encoderVelocity(currentState));
    double throttle = distanceController.retrieve();
    double turn = turnPid.calculate(currentState.getHeading());

    return new DriveSignal(throttle + turn, throttle - turn);
  }

  @Override
  public DriveState getCurrentState() {
    TrajectoryFollower1D.TrajectorySetpoint trajectorySetpoint = distanceController.getSetpoint();

    double dist = trajectorySetpoint.position;
    double velocity = trajectorySetpoint.velocity;

    return new DriveState(
        setpointRelativeDriveState.getLeftDistance() + dist,
        setpointRelativeDriveState.getRightDistance() + dist,
        setpointRelativeDriveState.getLeftVelocity() + velocity,
        setpointRelativeDriveState.getRightVelocity() + velocity,
        setpointRelativeDriveState.getHeading(),
        setpointRelativeDriveState.getHeadingVelocity());
  }

  public static double encoderVelocity(DriveState state) {
    return (state.getRightVelocity() + state.getLeftVelocity()) / 2.0;
  }

  public static double encoderDistance(DriveState state) {
    return (state.getRightDistance() + state.getLeftDistance()) / 2.0;
  }

  @Override
  public boolean isOnTarget() {
    return distanceController.isOnTarget();
  }
}
