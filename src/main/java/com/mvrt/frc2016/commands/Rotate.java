package com.mvrt.frc2016.commands;

import com.mvrt.frc2016.subsystems.Shooter;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 * Command to rotate shooter upon the axis it turns on.
 *
 * @author Siddharth Gollapudi
 */
public class Rotate extends Command {

  private final Shooter shooter;

  /**
   * Initialize the shooter object to rotate it when necessary.
   * @param shooter The shooter object needed to perform the command
   */
  public Rotate(Shooter shooter) {
    this.shooter = shooter;
    requires(this.shooter);
  }

  @Override protected void initialize() {

  }

  /**
   * Rotates the shooter upon its axis at a given speed.
   */
  @Override protected void execute() {
    /*
     * This depends on the type of operator control we decide on later.
     * For now, it will only rotate in a certain direction.
     */
    shooter.setSpeedRotate(0.1);
  }

  @Override protected boolean isFinished() {
    return false;
  }


  /**
   * When the command ends, stop the rotation.
   */
  @Override protected void end() {
    shooter.stopRotate();
  }

  @Override protected void interrupted() {

  }
}
