package com.mvrt.frc2016.subsystems;

import com.mvrt.lib.api.Runnable;
import com.mvrt.lib.api.Subsystem;

import java.util.concurrent.TimeUnit;

/**
 * The combined subsystem of the two flywheels and the arm.
 *
 * @author Lee Mracek
 */
public class Shiitake extends Subsystem implements Runnable {

  private final Flywheel leftFlywheel, rightFlywheel;
  private double referenceInput = 0;

  public Shiitake(String name, Flywheel leftFlywheel, Flywheel rightFlywheel) {
    super(name);

    this.leftFlywheel = leftFlywheel;
    this.rightFlywheel = rightFlywheel;
  }

  public void setFlywheelsRpm(double rpm) {
    this.referenceInput = rpm;
    this.leftFlywheel.setPwmRpm(rpm);
    this.rightFlywheel.setPwmRpm(rpm);
  }

  public void brakeFlywheels() {
    setFlywheelsRpm(0);
  }

  @Override
  public void run(long timeInMillis) {
    leftFlywheel.run(timeInMillis);
    rightFlywheel.run(timeInMillis);
  }
}
