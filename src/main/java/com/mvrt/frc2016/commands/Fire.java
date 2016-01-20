package com.mvrt.frc2016.commands;

import com.mvrt.frc2016.RobotManager;
import com.mvrt.frc2016.subsystems.Shooter;
import com.mvrt.frc2016.system.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Roll wheels outwards to shoot the boulder.
 *
 * @author Siddharth Gollapudi
 */
public class Fire extends Command {

  private final Shooter shooter;

  /**
   * Initialize the shooter object to fire when necessary.
   * @param shooter The shooter object needed to perform the command
   */
  public Fire(Shooter shooter) {
    //fix for nonstatic error
    this.shooter = shooter;
    requires(this.shooter);
  }

  @Override protected void initialize() {

  }

  @Override protected void execute() {
    shooter.setSpeedShoot(0.4);
  }

  @Override protected boolean isFinished() {
    return false;
  }

  /**
   * When the command ends, stop the shooting.
   */
  @Override protected void end() {
    shooter.stopShoot();
  }

  @Override protected void interrupted() {

  }
}
