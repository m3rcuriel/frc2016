package com.mvrt.lib.control;

import com.mvrt.lib.control.misc.DriveSignal;
import com.mvrt.lib.control.misc.DriveState;

/**
 * A controller specifically for the drive which uses the current robot state to generate a
 * DriveSignal response.
 *
 * @author Lee Mracek
 */
public abstract class DriveController extends Controller {
  /**
   * Calculate the desired DriveSignal based on the current state of the robot. This essentially
   * transforms a {@link DriveState} into an {@link DriveSignal}.
   *
   * @param currentState the current {@link DriveState}
   * @return the desired {@link DriveSignal}
   */
  public abstract DriveSignal update(DriveState currentState);

  /**
   * Retrieve the current {@link DriveState} from the controller.
   *
   * @return the {@link DriveState}
   */
  public abstract DriveState getCurrentState();

  @Override
  public void reset() {
    throw new UnsupportedOperationException("Sorry if you're iterating, but you can't reset a "
        + "drive controller!");
  }
}
