package com.mvrt.frc2016.auto.modes;

import com.mvrt.frc2016.auto.Auto;
import com.mvrt.frc2016.auto.AutoEndedException;
import com.mvrt.frc2016.system.Robot;

/**
 * An autonomous case which literally does nothing.
 */
public class DoNothingAuto extends Auto {

  public DoNothingAuto(Robot robot) {
    super(robot);
  }

  @Override
  protected  void routine() throws AutoEndedException {
  }

  @Override
  public void setup() {
  }
}
