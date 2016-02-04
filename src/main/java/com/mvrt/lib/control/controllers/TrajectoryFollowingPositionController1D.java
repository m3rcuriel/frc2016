package com.mvrt.lib.control.controllers;

import com.mvrt.frc2016.RobotManager;
import com.mvrt.lib.control.Controller;
import com.mvrt.lib.control.trajectory.TrajectoryFollower1D;

/**
 * Provides an object which extends {@link Controller} which can be used to follow a trajectory.
 *
 * @author Lee Mracek
 */
public class TrajectoryFollowingPositionController1D extends Controller {

  private TrajectoryFollower1D follower;
  double goal, error, onTargetDelta, result = 0;

  /**
   * Construct a new TrajectoryFollowingPositionController1D for the given constants and parameters.
   *
   * @param kP the proportional constant
   * @param kI the integral constant
   * @param kD the derivative constant
   * @param kV the velocity constant (1/max velocity)
   * @param kA the acceleration constant (normally 0)
   * @param onTargetDelta the allowed error
   * @param config the {@link com.mvrt.lib.control.trajectory.TrajectoryFollower1D.TrajectoryConfig}
   */
  public TrajectoryFollowingPositionController1D(double kP, double kI, double kD, double kV,
      double kA, double onTargetDelta, TrajectoryFollower1D.TrajectoryConfig config) {
    follower = new TrajectoryFollower1D();
    follower.initialize(kP, kI, kD, kV, kA, config);
    this.onTargetDelta = onTargetDelta;

    RobotManager.register("Command", () -> (int) (1000 * retrieve()));
  }

  /**
   * Set the goal of the TrajectoryFollower1D.
   *
   * @param currentState the current state of the robot
   * @param goal the goal position of the robot
   */
  public void setGoal(TrajectoryFollower1D.TrajectorySetpoint currentState, double goal) {
    this.goal = goal;
    follower.setGoal(currentState, goal);
  }

  /**
   * Retrieve the current goal.
   *
   * @return the current position goal
   */
  public double getGoal() {
    return follower.getGoal();
  }

  /**
   * Retrieve the configuration of the TrajectoryFollower1D.
   *
   * @return the configuration
   */
  public TrajectoryFollower1D.TrajectoryConfig getConfig() {
    return follower.getConfig();
  }

  /**
   * Update the calculated motor command (called at some constant period).
   *
   * @param position the position of the robot
   * @param velocity the velocity of the robot
   */
  public void update(double position, double velocity) {
    error = goal - position;
    result = follower.calculate(position, velocity);
  }

  /**
   * Retrieve the current setpoint of the TrajectoryFollower1D.
   *
   * @return the {@link com.mvrt.lib.control.trajectory.TrajectoryFollower1D.TrajectorySetpoint}
   */
  public TrajectoryFollower1D.TrajectorySetpoint getSetpoint() {
    return follower.getCurrentSetpoint();
  }

  /**
   * Reset the controller.
   */
  @Override
  public void reset() {
    result = 0;
    error = 0;
    follower.setGoal(follower.getCurrentSetpoint(), goal);
  }

  /**
   * Retrieve the corresponding method from the TrajectoryFollower1D.
   *
   * @return true if the TrajectoryFollower1D has completed the trajectory
   */
  public boolean isFinishedTrajectory() {
    return follower.isFinishedTrajectory();
  }

  /**
   * Retrieve the calculated command value.
   *
   * @return the calculated motor output
   */
  public double retrieve() {
    return result;
  }

  /**
   * Determine if the controller is on target.
   *
   * @return true if the controller is on target
   */
  @Override
  public boolean isOnTarget() {
    return follower.isFinishedTrajectory() && Math.abs(error) < onTargetDelta;
  }
}
