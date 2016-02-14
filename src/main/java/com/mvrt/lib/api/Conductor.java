package com.mvrt.lib.api;

import com.mvrt.lib.components.Clock;
import com.mvrt.lib.util.Metronome;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.LongConsumer;

/**
 * A conductor is used to invoke multiple registered
 * {@link Runnable}s on a fixed period using a {@link com.mvrt.lib.util.Metronome}.
 *
 * @author Lee Mracek
 */
public final class Conductor {
  private final String name;
  private final Clock time;
  private final Metronome metronome;
  private final Iterable<Runnable> runnables;
  private final AtomicReference<Thread> thread = new AtomicReference<>();
  private final LongConsumer delayInformer;
  private volatile boolean running = false;
  private volatile CountDownLatch stopped = null;

  public Conductor(String name, Iterable<Runnable> runnables, Clock time, Metronome metronome,
      LongConsumer delayInformer) {
    this.name = name;
    this.time = time;
    this.metronome = metronome;
    this.runnables = runnables;
    this.delayInformer = delayInformer != null ? delayInformer : Conductor::noDelay;
  }

  /**
   * Start the execution of the {@link Conductor} in a seperate thread. During each loop, all
   * registered {@link Runnable}s will be called in the order they were registered.
   * <br>
   * Calling this multiple times has no effect.
   */
  public void start() {
    thread.getAndUpdate(thread -> {
        if (thread == null) {
          thread = new Thread(this::run);
          thread.setName(name);
          thread.setPriority(8);
          stopped = new CountDownLatch(1);
          running = true;
          thread.start();
        }
        return thread;
      });
  }

  /**
   * Stops the execution of the conductor, and blocks until the tread has completed all work (or
   * the timeout of 10 seconds occurs)
   * <br>
   * Calling this multiple times has no effect.
   */
  public void stop() {
    CountDownLatch latch = stopped;

    Thread oldThread = thread.getAndUpdate(thread -> {
        if (thread != null) {
          running = false;
        }
        return null;
      });
    if (oldThread != null && latch != null) {
      try {
        latch.await(10, TimeUnit.SECONDS);
      } catch (InterruptedException e) {
        Thread.interrupted();
      }
    }
  }

  private void run() {
    try {
      long timeInMillis = 0L;
      long lastTimeInMillis = 0L;
      while (true) {
        timeInMillis = time.currentTimeInMillis();
        delayInformer.accept(timeInMillis - lastTimeInMillis);
        for (Runnable runnable : runnables) {
          if (!running) {
            return;
          }
          try {
            runnable.run(timeInMillis);
          } catch (Throwable e) {
            System.err.println("Runnable failed: " + runnable.toString());
          }
          lastTimeInMillis = timeInMillis;
        }
        metronome.pause();
      }
    } finally {
      CountDownLatch latch = stopped;
      latch.countDown();
      ;
    }
  }

  private static void noDelay(long delay) {
    // do nothing
  }
}
