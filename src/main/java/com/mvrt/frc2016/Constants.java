package com.mvrt.frc2016;

import com.mvrt.lib.api.ConstantsBase;

/**
 * A file holding public static constants for the robot.
 *
 * @author Lee Mracek
 */
public class Constants extends ConstantsBase {

  public static double kDriveDistancePerTick = 0;

  public static double kDriveMaxVelocity = 0;
  public static double kDriveMaxAcceleration = 0;

  public static double kDriveDistanceKp = 0;
  public static double kDriveDistanceKi = 0;
  public static double kDriveDistanceKd = 0;
  public static double kDriveDistanceKv = 0;
  public static double kDriveDistanceKa = 0;

  public static double kDriveOnTargetError = 1E-1;

  public static int kConstantDriveAcceptableBitwiseError = 8; // 8 bit precision

  public static double kDriveStraightKp = 0;
  public static double kDriveStraightKi = 0;
  public static double kDriveStraightKd = 0;

  public static double kFlywheelKp = 0;
  public static double kFlywheelKi = 0;
  public static double kFlywheelKd = 0;

  public static int kFlywheelAcceptableBitwiseError = 8; // default to 8 bit precision

  public static double kFlywheelDistancePerTick = 0;

  public static double kPresetBatterSpeed = 200;
  public static double kPresetIntakeSpeed = -200;

  public static double kConstantDriveKp = 0;
  public static double kConstantDriveKi = 0;
  public static double kConstantDriveKd = 0;

  public static int kEndEditableArea = 0;

  public static int kDriveLeftFrontId = 1;
  public static int kDriveLeftRearId = 2;
  public static int kDriveRightFrontId = 3;
  public static int kDriveRightRearId = 4;

  public static int kDriveJoystick = 0;

  public static int kDriveLeftFrontEncoderA = 0;
  public static int kDriveLeftFrontEncoderB = 1;
  public static int kDriveRightFrontEncoderA = 2;
  public static int kDriveRightFrontEncoderB = 3;

  public static int kLeftFlywheelMotor = 5;
  public static int kLeftFlywheelEncoderA = 4;
  public static int kLeftFlywheelEncoderB = 5;

  public static int kRightFlywheelMotor = 6;
  public static int kRightFlywheelEncoderA = 6;
  public static int kRightFlywheelEncoderB = 7;


  /**
   * Allows the programmer to customize the location of the constants save file.
   *
   * @return the string path of the constants file
   */
  @Override
  public String getFileLocation() {
    return "~/constants.txt";
  }

  static {
    new Constants().loadFromFile();
  }
}
