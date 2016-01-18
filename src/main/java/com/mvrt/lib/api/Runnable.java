package com.mvrt.lib.api;

import java.util.concurrent.TimeUnit;

/**
 * An interface for any thing which can be run.
 */
public interface Runnable {

  default void run(long timeInMillis) {
    run(timeInMillis, TimeUnit.MILLISECONDS);
  }

  void run(long time, TimeUnit unit);
}
