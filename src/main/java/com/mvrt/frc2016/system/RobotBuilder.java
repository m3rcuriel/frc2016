package com.mvrt.frc2016.system;

import com.mvrt.frc2016.Constants;
import com.mvrt.lib.components.DriveTrain;
import com.mvrt.lib.components.Motor;
import com.mvrt.lib.components.SimpleAccumulatedSensor;
import com.mvrt.lib.hardware.Hardware;

/**
 * The builder class for the robot. Instantiates the {@link Robot} which will be used, as well as
 * creating wrappers for each component and system using {@link Hardware}.
 *
 * @author Lee Mracek
 */
public class RobotBuilder {

  /**
   * Build a new robot.
   *
   * @return the new {@link Robot}
   */
  public static Robot buildRobot() {
    Components components = new Components();

    DriveTrain driveTrain = DriveTrain
        .create(Motor.invert(Motor.compose(components.leftFront, components.leftRear)),
            Motor.compose(components.rightFront, components.rightRear));

    DriveInterpreter drive = new DriveInterpreter(driveTrain);

    OperatorInterface operator = new OperatorInterface(
        Hardware.HumanInterfaceDevices.logitechAttack3dPro(Constants.kDriveJoystick));

    return new Robot(drive, operator, components);
  }

  /**
   * An inner class representing the lower level components on the Robot.
   *
   * @author Lee Mracek
   */
  public static final class Components {
    public Components() {
    }

    // Drive
    public final Motor leftFront = Hardware.Motors.talonSrxRaw(Constants.kDriveLeftFrontId);
    public final Motor leftRear = Hardware.Motors.talonSrxRaw(Constants.kDriveLeftRearId);
    public final Motor rightFront = Hardware.Motors.talonSrxRaw(Constants.kDriveRightFrontId);
    public final Motor rightRear = Hardware.Motors.talonSrxRaw(Constants.kDriveRightRearId);

    public final SimpleAccumulatedSensor leftFrontEncoder = Hardware.AccumulatedSensors
        .quadEncoder(Constants.kDriveLeftFrontEncoderA, Constants.kDriveLeftFrontEncoderB,
            Constants.kDriveDistancePerTick);
    public final SimpleAccumulatedSensor rightFrontEncoder = Hardware.AccumulatedSensors
        .quadEncoder(Constants.kDriveRightFrontEncoderA, Constants.kDriveRightFrontEncoderB,
            Constants.kDriveDistancePerTick);
  }
}
