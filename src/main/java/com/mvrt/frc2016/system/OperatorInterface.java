package com.mvrt.frc2016.system;

import com.mvrt.lib.components.Switch;
import com.mvrt.lib.components.oi.ContinuousRange;
import com.mvrt.lib.components.oi.FlightStick;

public class OperatorInterface {
  public final ContinuousRange wheel;
  public final ContinuousRange throttle;

  public final Switch quickturn;

  public final Switch reverse;

  public final Switch intake;

  public final Switch shooterOff;

  // Presets
  public final Switch batterPresetShot;

  public OperatorInterface(FlightStick driveStick) {
    throttle = driveStick.getPitch();
    wheel = driveStick.getRoll();

    quickturn = driveStick.getTrigger();

    reverse = driveStick.getThumb();

    intake = driveStick.getButton(11);

    shooterOff = driveStick.getButton(12);

    batterPresetShot = driveStick.getButton(7);
  }
}
