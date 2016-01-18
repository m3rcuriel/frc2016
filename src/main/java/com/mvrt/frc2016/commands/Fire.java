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

  public Fire(Shooter shooter) {
    //fix for nonstatic error
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
