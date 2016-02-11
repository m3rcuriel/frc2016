package com.mvrt.frc2016.system;

import com.kauailabs.navx.frc.AHRS;
import com.mvrt.frc2016.subsystems.DriveSystem;
import com.mvrt.frc2016.subsystems.Shiitake;

/**
 * The class which contains all components and subsystems of the robot. Used to access these methods
 * from a static context.
 */
public class Robot {
  public final DriveInterpreter drive;
  public final OperatorInterface operator;
  public final AHRS navX;
  public final RobotBuilder.Components components;
  public final DriveSystem driveSystem;
  public final Shiitake shiitake;

  /**
   * Constructs a new robot with the given subsystems.
   *
   * @param drive      the {@link DriveInterpreter} on the robot
   * @param operator   the {@link OperatorInterface} instance used with the robot
   * @param navX       the {@link AHRS} mounted to the robot
   * @param components the {@link RobotBuilder.Components} representing miscellaneous parts
   */
  public Robot(DriveInterpreter drive, OperatorInterface operator, AHRS navX,
      RobotBuilder.Components components, DriveSystem driveSystem, Shiitake shiitake) {
    this.drive = drive;
    this.operator = operator;
    this.navX = navX;
    this.components = components;
    this.driveSystem = driveSystem;
    this.shiitake = shiitake;
  }
}
