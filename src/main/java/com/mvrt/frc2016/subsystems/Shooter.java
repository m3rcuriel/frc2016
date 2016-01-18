package com.mvrt.frc2016.subsystems;

import com.mvrt.frc2016.Constants;
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


  public Shooter() {
    leftShoot = new CANTalon(Constants.leftShooterId);
    rightShoot = new CANTalon(Constants.rightShooterId);
    rotate = new CANTalon(Constants.rotateAxisId);

    leftShoot.setFeedbackDevice(CANTalon.FeedbackDevice.AnalogEncoder);

    rightShoot.changeControlMode(CANTalon.TalonControlMode.Follower);
    rightShoot.set(leftShoot.getDeviceID());
  }

  //TODO: add the code for the rotating stuff
  @Override protected void initDefaultCommand() {

  }

  public void stop() {
    leftShoot.set(0);
  }

  public void setSpeed(double spd) {
    leftShoot.set(spd);
  }
}
