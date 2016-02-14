package com.mvrt.lib.api;

import java.util.concurrent.TimeUnit;

/**
 * An interface for any thing which can be run.
 */
public interface Runnable {

  void run(long timeInMillis);

  default void run(long time, TimeUnit unit) {
    run(unit.convert(time, TimeUnit.MILLISECONDS));
  }

  @Override
  String toString();
}
