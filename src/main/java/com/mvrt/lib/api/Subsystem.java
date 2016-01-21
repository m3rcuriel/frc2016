package com.mvrt.lib.api;

/**
 * An abstract representation of a subsystem. Will be used to register each subsystem with
 * Bullboard and the logger.
 *
 * @author Lee Mracek
 */
public abstract class Subsystem {
  private String name;

  /**
   * Construct a new subsystem with a name for retrieval.
   *
   * @param name the name of the subsystem
   */
  public Subsystem(String name) {
    this.name = name;
  }

  /**
   * Retrieve the name of the Subsystem.
   *
   * @return the name of the Subsystem
   */
  public String getName() {
    return name;
  }
}
