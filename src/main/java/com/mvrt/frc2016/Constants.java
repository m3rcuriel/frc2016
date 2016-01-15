package com.mvrt.frc2016;

import com.mvrt.lib.api.ConstantsBase;

/**
 * A file holding public static constants for the robot.
 *
 * @author Lee Mracek
 */
public class Constants extends ConstantsBase {

  /**
   * Allows the programmer to customize the location of the constants save file.
   *
   * @return the string path of the constants file
   */
  @Override public String getFileLocation() {
    return "~/constants.txt";
  }

  static {
    new Constants().loadFromFile();
  }
}
