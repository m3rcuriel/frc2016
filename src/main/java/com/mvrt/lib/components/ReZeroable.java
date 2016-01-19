package com.mvrt.lib.components;

/**
 * Interface representing object that can have new zero; returns new zero.
 *
 * @author Siddharth Gollapudi
 */
public interface ReZeroable {

  /**
   *
   * Get a new zero, return it.
   *
   * @return zeroed object
   */
  public ReZeroable zero();
}
