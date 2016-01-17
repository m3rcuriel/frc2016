package com.mvrt.frc2016;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 * Base class for the robot. This is the class which will run when the code is initialized on the
 * RoboRIO. Many optional methods can be overridden in addition to the periodic methods.
 *
 * @author Lee Mracek
 */
public class RobotManager extends IterativeRobot {

  public enum RobotState {
    DISABLED, AUTONOMOUS, TELEOP;
  }


  private static Robot robot;

  /**
   * Get the robot subsystem representation.
   *
   * @return the full {@link Robot}
   */
  public Robot get() {
    return robot;
  }

  private static RobotState robotState = RobotState.DISABLED;

  /**
   * Retrieves the current enum state of the robot.
   *
   * @return the RobotState
   */
  public static RobotState getState() {
    return robotState;
  }

  /**
   * Runs when the robot is initially turned on.
   */
  @Override
  public void robotInit() {
    robot = RobotBuilder.buildRobot();
  }

  /**
   * Runs once when the robot enters autonomous.
   */
  @Override
  public void autonomousInit() {
    robotState = RobotState.AUTONOMOUS;
  }

  /**
   * Loops every approximately 20 ms during the autonomous period.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * Runs once when the robot enteres teleop.
   */
  @Override
  public void teleopInit() {
    robotState = RobotState.TELEOP;
  }

  /**
   * Loops every approximately 20 ms during the teleoperated period.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();

    double throttle = (robot.operator.throttle.read());
    double wheel = (robot.operator.wheel.read());

    robot.drive.austinDrive(throttle, wheel, robot.operator.quickturn.isTriggered());
  }

  /**
   * Runs whenever the robot enters disabled.
   */
  @Override
  public void disabledInit() {
    robotState = RobotState.DISABLED;
  }

  /**
   * Loops every approximately 20 ms during the disabled period.
   */
  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }
}
