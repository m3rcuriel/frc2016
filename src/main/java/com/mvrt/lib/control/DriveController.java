package com.mvrt.lib.control;

import com.mvrt.lib.control.misc.DriveSignal;
import com.mvrt.lib.control.misc.DriveState;

/**
 * @author Lee Mracek
 */
public abstract class DriveController extends Controller {
  public abstract DriveSignal update(DriveState currentState);

  public abstract DriveState getCurrentState();

  @Override
  public void reset() {
    throw new UnsupportedOperationException("Sorry if you're iterating, but you can't reset a "
        + "drive controller!");
  }
}
