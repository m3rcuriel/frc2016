package com.mvrt.frc2016.auto.actions;

import com.mvrt.frc2016.RobotManager;
import com.mvrt.frc2016.system.Robot;
import org.junit.rules.Timeout;

import java.util.concurrent.TimeUnit;

/**
 * Created by lee on 1/25/16.
 */
public class TimeoutAction extends Action {
  private double timeout;
  private double startTime;

  public TimeoutAction(Robot robot, long timeout, TimeUnit unit) {
    super(robot);
    this.timeout = TimeUnit.MILLISECONDS.convert(timeout, unit);
  }

  @Override
  public boolean isFinished() {
    return RobotManager.getRobotTimeMillis() >= startTime + timeout;
  }

  @Override
  public void update() {
  }

  @Override
  public void done() {
  }

  @Override
  public void start() {
    startTime = RobotManager.getRobotTimeMillis();
  }
}
