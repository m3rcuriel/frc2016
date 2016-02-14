package com.mvrt.frc2016.subsystems.Controllers;

import com.mvrt.lib.control.Controller;

/**
 * Created by siddharth on 2/13/16.
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
