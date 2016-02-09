package com.mvrt.frc2016.auto;

/**
 * This runs an autonomous mode. Theoretically this would be part of the existing controller
 * framework, but it makes more sense separately for now.
 *
 * TODO(m3rcuriel): Integrate into existing controller framework
 */
public class AutoConductor {
  private AutoBase auto;
  private Thread thread = null;

  /**
   * Set the autonomous mode to run.
   *
   * @param auto the autonomous mode to run
   */
  public void setAuto(AutoBase auto) {
    this.auto = auto;
  }

  /**
   * Start the AutoConductor.
   *
   * Note that this does nothing if the AutoConductor is already running.
   */
  public void start() {
    if (thread == null) {
      thread = new Thread(() -> {
          if (auto != null) {
            auto.run();
          }
        });
      thread.start();
    }
  }

  /**
   * Stop the AutoConductor.
   *
   * Note that this does nothing if the AutoConductor is already stopped.
   */
  public void stop() {
    if (auto != null) {
      auto.stop();
    }
    thread = null;
  }
}
