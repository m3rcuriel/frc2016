package com.mvrt.frc2016.subsystems;

import com.mvrt.frc2016.Constants;
import com.mvrt.lib.components.Motor;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 * Code for the Shooter subsystem. Used in the Rotate, Fire, and Intake commands.
 *
 * @author Siddharth Gollapudi
 */
public class Shooter extends Subsystem {

  private CANTalon leftShoot;
  private CANTalon rightShoot;
  private CANTalon rotate;


  /**
   * Initialize all the motors controllers, and set them up for use.
   */
  public Shooter() {
    leftShoot = new CANTalon(Constants.leftShooterId);
    rightShoot = new CANTalon(Constants.rightShooterId);
    rotate = new CANTalon(Constants.rotateAxisId);

    leftShoot.setFeedbackDevice(CANTalon.FeedbackDevice.AnalogEncoder);

    rightShoot.changeControlMode(CANTalon.TalonControlMode.Follower);
    rightShoot.set(leftShoot.getDeviceID());
  }

  @Override protected void initDefaultCommand() {

  }

  /**
   * Stop the shooting.
   */
  public void stopShoot() {
    leftShoot.set(0);
  }

  /**
   * Stop the rotating.
   */
  public void stopRotate() {
    rotate.set(0.2);
  }

  /**
   * Set the rotation to occur at the given speed.
   * @param speed speed at which shooter rotates
   */
  public void setSpeedRotate(double speed) {
    rotate.set(speed);
  }

  /**
   * Set the wheels to either intake or fire, depending on speed value.
   * @param spd speed at which the wheels rotate
   */
  public void setSpeedShoot(double spd) {
    leftShoot.set(spd);
  }
}
