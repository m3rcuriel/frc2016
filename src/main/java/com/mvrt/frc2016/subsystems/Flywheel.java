package com.mvrt.frc2016.subsystems;

import com.mvrt.frc2016.Constants;
import com.mvrt.frc2016.subsystems.controllers.FlywheelController;
import com.mvrt.frc2016.subsystems.controllers.FlywheelPidController;
import com.mvrt.lib.api.Runnable;
import com.mvrt.lib.api.Subsystem;
import com.mvrt.lib.components.Motor;
import com.mvrt.lib.components.SimpleAccumulatedSensor;
import com.mvrt.lib.control.misc.PidConstants;

/**
 * A representation of one of the flywheels on the shooter.
 *
 * @author Lee Mracek
 */
public class Flywheel extends Subsystem implements Runnable {

  private double referenceInput = 0; // value in RPM

  private final Motor flywheelMotor;
  private final SimpleAccumulatedSensor flywheelSensor;

  private FlywheelController controller = null;

  /**
   * Construct a new subsystem with a name for retrieval.
   *
   * @param name the name of the subsystem
   */
  public Flywheel(String name, Motor flywheelMotor, SimpleAccumulatedSensor flywheelSensor) {
    super(name);

    this.flywheelMotor = flywheelMotor;
    this.flywheelSensor = flywheelSensor;
  }

  public void setPwmRpm(double rpm) {
    if (!(controller instanceof FlywheelPidController)) {
      controller = new FlywheelPidController(
          new PidConstants(Constants.kFlywheelKp, Constants.kFlywheelKi, Constants.kFlywheelKd),
          Constants.kFlywheelAcceptableBitwiseError);
      controller.setGoal(rpm);
    } else {
      controller.setGoal(rpm);
    }

    referenceInput = rpm;
  }

  public void setVelocityRaw(double power) {
    flywheelMotor.setSpeed(power);
  }

  @Override
  public void run(long timeInMillis) {
    controller.update(flywheelSensor.getPosition(), flywheelSensor.getRate());
    flywheelMotor.setSpeed(controller.get());
  }
}
