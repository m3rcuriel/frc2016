package com.mvrt.lib.control.trajectory;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TrajectoryFollower1DTest {
  @Test
  public void test() {
    TrajectoryFollower1D follower = new TrajectoryFollower1D();
    TrajectoryFollower1D.TrajectoryConfig config = new TrajectoryFollower1D.TrajectoryConfig();
    config.dt = 0.005; // 20 ms dt
    config.maxAcceleration = 180.0;
    config.maxVelocity = 60.0;
    TrajectoryFollower1D.TrajectorySetpoint setpoint =
        new TrajectoryFollower1D.TrajectorySetpoint();
    setpoint.acceleration = 0;
    setpoint.velocity = 0;
    setpoint.position = 0;

    follower.initialize(0.1, 0.0, 0.0, 1.0, 0.0, config);
    follower.setGoal(setpoint, 100.0);

    int cycles = 0;
    while (!follower.isFinishedTrajectory()) {
      loop(follower, setpoint, config.dt);
      cycles++;
    }

    setpoint = follower.getCurrentSetpoint();
    System.out.println("Took " + cycles + " cycles; " + cycles * config.dt + " seconds");
    assertEquals(setpoint.position, 100.0, 1E-3);
    assertEquals(setpoint.velocity, 0.0, 1E-3);

    follower.setGoal(setpoint, 0.0);
    cycles = 0;
    while (!follower.isFinishedTrajectory()) {
      loop(follower, setpoint, config.dt);
      cycles++;
    }

    setpoint = follower.getCurrentSetpoint();
    System.out.println("Took " + cycles + " cycles; " + cycles * config.dt + " seconds");
    assertEquals(setpoint.position, 0.0, 1E-3);
    assertEquals(setpoint.velocity, 0.0, 1E-3);

    setpoint.position = 0;
    setpoint.velocity = 60;
    setpoint.acceleration = 0;
    follower.setGoal(setpoint, 2.0);
    cycles = 0;
    while (!follower.isFinishedTrajectory()) {
      loop(follower, setpoint, config.dt);
      cycles++;
    }

    setpoint = follower.getCurrentSetpoint();
    System.out.println("Took " + cycles + " cycles; " + cycles * config.dt + " seconds");
    assertEquals(setpoint.position, 2.0, 1E-3);
    assertEquals(setpoint.velocity, 0.0, 1E-3);
  }

  private void loop(TrajectoryFollower1D follower, TrajectoryFollower1D.TrajectorySetpoint setpoint,
      double dt) {
    double command = follower.calculate(setpoint.position, setpoint.velocity);
    setpoint = follower.getCurrentSetpoint();
    System.out.println("Result: " + setpoint + "; Command: " + command);
  }
}
