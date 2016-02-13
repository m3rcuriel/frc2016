package com.mvrt.frc2016.subsystems.controllers;

import com.mvrt.lib.components.Motor;
import com.mvrt.lib.control.Controller;

/**
 * Controller to constantly set the output of a drive motor to the maximum.
 *
 * @author Siddharth Gollapudi
 */
public class ConstantSpeedController extends Controller {

  private int speed;
  private Motor motor;

  public ConstantSpeedController(int speed, Motor motor) {
    this.speed = speed;
    this.motor = motor;
  }

  public void update(int speed) {
    motor.setSpeed(speed);
  }

  @Override
  public void reset() {
    speed = 0;
  }

  @Override
  public boolean isOnTarget() {
    if(motor.getSpeed() == speed) {
      return true;
    } else {
      return false;
    }
  }
}
