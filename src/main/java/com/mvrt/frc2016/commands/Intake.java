package com.mvrt.frc2016.commands;

import com.mvrt.frc2016.subsystems.Shooter;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 * Rotate the wheels inward to intake boulders.
 *
 * @author Siddharth
 */
public class Intake extends Command {

  private final Shooter shooter;

  public Intake(Shooter shooter) {
    this.shooter = shooter;
    requires(shooter);
  }

  @Override protected void initialize() {

  }

  @Override protected void execute() {

  }

  @Override protected boolean isFinished() {
    return false;
  }

  @Override protected void end() {

  }

  @Override protected void interrupted() {

  }
}
