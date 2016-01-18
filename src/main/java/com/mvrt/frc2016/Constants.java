package com.mvrt.frc2016;

import com.mvrt.lib.api.ConstantsBase;

/**
 * A file holding public static constants for the robot.
 *
 * @author Lee Mracek
 */
public class Constants extends ConstantsBase {

  public static int kEndEditableArea = 0;

  public static int kDriveLeftFrontId = 1;
  public static int kDriveLeftRearId = 2;
  public static int kDriveRightFrontId = 3;
  public static int kDriveRightRearId = 4;

  public static int kDriveJoystick = 0;

  public static int leftShooterId = 5;
  public static int rightShooterId = 6;
  public static int rotateAxisId = 7;

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
