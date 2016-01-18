package com.mvrt.lib.api;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Container for a Interable of Runnable.
 *
 * @author Lee Mracek
 */
public class Runnables implements Iterable<Runnable> {
  private final CopyOnWriteArrayList<Runnable> runnables = new CopyOnWriteArrayList<>();

  public Runnables() {
  }

  /**
   * Registers a new Runnable.
   *
   * @param r the runnable to register
   * @return true if the Runnable was added
   */
  public boolean register(Runnable r) {
    return runnables.add(r);
  }

  /**
   * Unregisters a Runnable object.
   *
   * @param r the Runnable to unregister
   * @return true if the Runnable is unregistered
   */
  public boolean unregister(Runnable r) {
    return runnables.remove(r);
  }

  /**
   * Unregister all {@link Runnable}s.
   */
  public void unregisterAll() {
    runnables.clear();
  }

  @Override
  public Iterator<Runnable> iterator() {
    return runnables.iterator();
  }

  /**
   * Check if the instance is empty.
   *
   * @return true if the instance has no registered {@link Runnable}s
   */
  public boolean isEmpty() {
    return runnables.isEmpty();
  }
}
