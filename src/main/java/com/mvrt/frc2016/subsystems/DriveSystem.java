package com.mvrt.frc2016.subsystems;

import com.mvrt.lib.api.Runnable;
import com.mvrt.lib.api.Subsystem;
import com.mvrt.lib.components.DriveTrain;
import com.mvrt.lib.components.Gyroscope;
import com.mvrt.lib.components.Motor;
import com.mvrt.lib.components.SimpleAccumulatedSensor;
import com.mvrt.lib.control.DriveController;
import com.mvrt.lib.control.misc.DriveSignal;
import com.mvrt.lib.control.misc.DriveState;

import java.util.concurrent.TimeUnit;

/**
 * This year's Robot drive train subsystem.
 *
 * @author Lee Mracek
 */
public class DriveSystem extends Subsystem implements DriveTrain, Runnable {

  private final Motor leftMotor, rightMotor;
  private final SimpleAccumulatedSensor leftEncoder, rightEncoder;
  private final Gyroscope gyroscope;

  private DriveController controller = null;

  private DriveState driveState = new DriveState(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);

  public DriveSystem(String name, Motor leftMotor, Motor rightMotor,
      SimpleAccumulatedSensor leftEncoder, SimpleAccumulatedSensor rightEncoder,
      Gyroscope gyroscope) {
    super(name);
    this.leftMotor = leftMotor;
    this.rightMotor = rightMotor;
    this.leftEncoder = leftEncoder;
    this.rightEncoder = rightEncoder;
    this.gyroscope = gyroscope;
  }

  public void setOpenLoop(DriveSignal signal) {
    controller = null;
    this.drive(signal);
  }

  public void reset() {
    leftEncoder.zero();
    rightEncoder.zero();
  }

  @Override
  public void drive(DriveSignal signal) {
    this.leftMotor.setSpeed(signal.leftMotor);
    this.rightMotor.setSpeed(signal.rightMotor);
  }

  @Override
  public void run(long time, TimeUnit unit) {
    if (controller == null) {
      return;
    }
    drive(controller.update(getDriveState()));
  }

  public DriveController getController() {
    return controller;
  }

  public DriveState getDriveState() {
    driveState.reset(leftEncoder.getPosition(), rightEncoder.getPosition(), leftEncoder.getRate(),
        rightEncoder.getRate(), gyroscope.getHeading(), gyroscope.getRate());
    return driveState;
  }
}
