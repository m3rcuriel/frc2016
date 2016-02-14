package com.mvrt.frc2016.system;

import com.kauailabs.navx.frc.AHRS;
import com.mvrt.frc2016.Constants;
import com.mvrt.frc2016.subsystems.DriveSystem;
import com.mvrt.frc2016.subsystems.Flywheel;
import com.mvrt.frc2016.subsystems.Shiitake;
import com.mvrt.lib.api.ConstantsBase;
import com.mvrt.lib.components.Motor;
import com.mvrt.lib.components.SimpleAccumulatedSensor;
import com.mvrt.lib.hardware.Hardware;
import edu.wpi.first.wpilibj.SPI;

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

    DriveSystem driveSystem = new DriveSystem("Drive System",
        Motor.compose(components.leftFront, components.leftRear),
        Motor.invert(Motor.compose(components.rightFront, components.rightRear)), components.leftFrontEncoder,
        components.rightFrontEncoder, Hardware.ahrsAsGyroscope(components.navX));

    DriveInterpreter drive = new DriveInterpreter(driveSystem);

    OperatorInterface operator = new OperatorInterface(
        Hardware.HumanInterfaceDevices.logitechAttack3dPro(Constants.kDriveJoystick));

    Flywheel leftFlywheel = new Flywheel("Left Flywheel", components.leftFlywheelMotor,
        components.leftFlywheelEncoder);
    Flywheel rightFlywheel = new Flywheel("Right Flywheel", components.rightFlywheelMotor,
        components.rightFlywheelEncoder);

    Shiitake shiitake = new Shiitake("Shiitake", leftFlywheel, rightFlywheel);

    return new Robot(drive, operator, components.navX, components, driveSystem, shiitake);
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
    public final SimpleAccumulatedSensor rightFrontEncoder = SimpleAccumulatedSensor.invert(
        Hardware.AccumulatedSensors
            .quadEncoder(Constants.kDriveRightFrontEncoderA, Constants.kDriveRightFrontEncoderB,
                Constants.kDriveDistancePerTick));

    public final AHRS navX = new AHRS(SPI.Port.kMXP);

    public final Motor leftFlywheelMotor = Hardware.Motors.talonSrxRaw(Constants
        .kLeftFlywheelMotor);
    public final SimpleAccumulatedSensor leftFlywheelEncoder =
        Hardware.AccumulatedSensors.quadEncoder(Constants.kLeftFlywheelEncoderA,
            Constants.kLeftFlywheelEncoderB, Constants.kFlywheelDistancePerTick);
    public final Motor rightFlywheelMotor = Hardware.Motors.talonSrxRaw(Constants
        .kRightFlywheelMotor);
    public final SimpleAccumulatedSensor rightFlywheelEncoder =
        SimpleAccumulatedSensor.invert(Hardware.AccumulatedSensors.quadEncoder(Constants
            .kRightFlywheelEncoderA, Constants.kRightFlywheelEncoderB, Constants
            .kFlywheelDistancePerTick));
  }
}
