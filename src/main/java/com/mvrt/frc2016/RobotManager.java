package com.mvrt.frc2016;

import com.mvrt.frc2016.system.Robot;
import com.mvrt.frc2016.system.RobotBuilder;
import com.mvrt.lib.api.Conductor;
import com.mvrt.lib.api.Runnables;
import com.mvrt.lib.components.Clock;
import com.mvrt.lib.control.misc.DriveSignal;
import com.mvrt.lib.util.Metronome;
import edu.wpi.first.wpilibj.IterativeRobot;

import java.util.concurrent.TimeUnit;

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

  public static final long AMBIENT_MILLISECONDS = 30;

  private static Conductor ambientConductor;
  private static Runnables ambientRunnables;
  private static Metronome ambientMetronome;

  public static final long CONTROLLERS_MILLISECONDS = 5;

  private static Conductor controllersConductor;
  private static Runnables controllersRunnables;
  private static Metronome controllersMetronome;

  private static Clock robotClock;

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

    robotClock = Clock.fpgaOrSystem();

    controllersRunnables = new Runnables();
    controllersMetronome = Metronome.metronome(CONTROLLERS_MILLISECONDS, TimeUnit.MILLISECONDS,
        robotClock);
    controllersConductor = new Conductor("Controllers Conductor", controllersRunnables,
        robotClock, controllersMetronome, null);

    controllersRunnables.register(robot.driveSystem);

    ambientRunnables = new Runnables();

    ambientMetronome = Metronome.metronome(AMBIENT_MILLISECONDS, TimeUnit.MILLISECONDS, robotClock);
    ambientConductor =
        new Conductor("Ambient Conductor", ambientRunnables, robotClock, ambientMetronome, null);

    ambientConductor.start();
  }

  /**
   * Runs once when the robot enters autonomous.
   */
  @Override
  public void autonomousInit() {
    robotState = RobotState.AUTONOMOUS;

    robot.driveSystem.reset();

    controllersConductor.start();
  }

  /**
   * Loops every approximately 20 ms during the autonomous period.
   */
  @Override
  public void autonomousPeriodic() {
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

    controllersConductor.stop();

    robot.driveSystem.setOpenLoop(DriveSignal.NEUTRAL);

    System.gc();
  }

  /**
   * Loops every approximately 20 ms during the disabled period.
   */
  @Override
  public void disabledPeriodic() {
  }
}
