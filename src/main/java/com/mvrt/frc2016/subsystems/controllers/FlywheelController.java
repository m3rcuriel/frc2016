package com.mvrt.frc2016.subsystems.controllers;

import com.mvrt.lib.control.Controller;

/**
 * A general controller for the {@link com.mvrt.frc2016.subsystems.Flywheel}s.
 *
 * @author Lee Mracek
 */
public abstract class FlywheelController extends Controller {

  protected double powerOutput = 0;
  protected double goal = 0;

  public void setGoal(double goal) {
    this.goal = goal;
  }

  public double getGoal() {
    return goal;
  }

  public abstract void update(double position, double velocity);

  public double get() {
    return powerOutput;
  }
}
