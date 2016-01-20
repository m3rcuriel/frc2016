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

  /**
   * Initialize the shooter object to intake when necessary.
   * @param shooter The shooter object needed to perform the command
   */
  public Intake(Shooter shooter) {
    this.shooter = shooter;
    requires(this.shooter);
  }

  @Override protected void initialize() {

  }

  @Override protected void execute() {
    shooter.setSpeedShoot(-0.4);
  }

  @Override protected boolean isFinished() {
    return false;
  }

  /**
   * When the command ends, stop the intaking.
   */
  @Override protected void end() {

    shooter.stopShoot();
  }

  @Override protected void interrupted() {

  }
}
