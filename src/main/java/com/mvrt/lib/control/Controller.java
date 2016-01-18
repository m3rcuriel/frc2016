package com.mvrt.lib.control;

public abstract class Controller<T> {
  protected boolean enabled = false;

  public abstract void reset();

  public abstract T retrieve();

  public abstract boolean isOnTarget();
}
