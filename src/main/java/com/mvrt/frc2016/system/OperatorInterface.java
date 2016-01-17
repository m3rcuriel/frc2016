package com.mvrt.frc2016.system;

import com.mvrt.lib.components.Switch;
import com.mvrt.lib.components.oi.ContinuousRange;
import com.mvrt.lib.components.oi.FlightStick;

public class OperatorInterface {
  public final ContinuousRange wheel;
  public final ContinuousRange throttle;

  public final Switch quickturn;

  public OperatorInterface(FlightStick driveStick) {
    throttle = driveStick.getPitch();
    wheel = driveStick.getRoll();

    quickturn = driveStick.getButton(5);
  }
}
