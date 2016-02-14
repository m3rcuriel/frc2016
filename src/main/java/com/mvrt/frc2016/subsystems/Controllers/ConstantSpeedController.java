package com.mvrt.frc2016.subsystems.controllers;

import com.mvrt.lib.control.Controller;

/**
 * Abstract implementation of the constant speed controller.
 *
 * @author Siddharth Gollapudi
 */
public abstract class ConstantSpeedController extends Controller {

  protected double powerOutput = 0;
  protected double goal = 0;

  public void setGoal(double goal) {
    this.goal = goal;
  }

  public double getGoal() {
    return goal;
  }

  public abstract void update(double velocity);

  public double get() {
    return powerOutput;
  }
}
