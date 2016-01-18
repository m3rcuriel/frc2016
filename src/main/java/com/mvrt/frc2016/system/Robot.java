package com.mvrt.frc2016.system;

import com.mvrt.frc2016.subsystems.Shooter;

/**
 * The class which contains all components and subsystems of the robot. Used to access these methods
 * from a static context.
 */
public class Robot {
  public final DriveInterpreter drive;
  public final OperatorInterface operator;
  public final Shooter shooter;

  /**
   * Constructs a new robot with the given subsystems.
   *
   * @param drive    the {@link DriveInterpreter} on the robot
   * @param operator the {@link OperatorInterface} instance used with the robot
   * @param shooter  the {@link Shooter} used on the robot
   */
  public Robot(DriveInterpreter drive, OperatorInterface operator, Shooter shooter) {
    this.drive = drive;
    this.operator = operator;
    this.shooter = shooter;

  }
}
