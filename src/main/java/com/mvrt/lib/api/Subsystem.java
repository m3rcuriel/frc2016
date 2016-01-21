package com.mvrt.lib.api;

/**
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
