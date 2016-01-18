package com.mvrt.frc2016.system;

/**
 * The class which contains all components and subsystems of the robot. Used to access these methods
 * from a static context.
 */
public class Robot {
  public final DriveInterpreter drive;
  public final OperatorInterface operator;
  public final RobotBuilder.Components components;

  /**
   * Constructs a new robot with the given subsystems.
   *
   * @param drive    the {@link DriveInterpreter} on the robot
   * @param operator the {@link OperatorInterface} instance used with the robot
   */
  public Robot(DriveInterpreter drive, OperatorInterface operator,
      RobotBuilder.Components components) {
    this.drive = drive;
    this.operator = operator;
    this.components = components;
  }
}
