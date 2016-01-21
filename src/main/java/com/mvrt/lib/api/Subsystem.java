package com.mvrt.lib.api;

/**
 * An abstract representation of a subsystem. Will be used to register each subsystem with
 * Bullboard and the logger.
 *
 * @author Lee Mracek
 */
public abstract class Subsystem {
  private String name;

  public Subsystem(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
