package com.mvrt.lib.util;

import java.util.Arrays;

public class DoubleMatrix {
  private double[] data;
  private int width;
  private int height;

  /**
   * Construct a DoubleMatrix instance of the given size.
   *
   * @param width the width of the new matrix
   * @param height the height of the new matrix
   */
  public DoubleMatrix(int width, int height) {
    this.width = width;
    this.height = height;
    data = new double[width * height];
    Arrays.fill(data, 0.0);
  }

  /**
   * Construct a new DoubleMatrix with the given single array data.
   *
   * @param height the height of the matrix
   * @param width the width of the matrix
   * @param data the 1D array representing the data
   */
  public DoubleMatrix(int width, int height, double[] data) {
    this.width = width;
    this.height = height;
    this.data = Arrays.copyOf(data, data.length);
  }

  /**
   * Construct a new DoubleMatrix from a 2D array. For example: {{ 0, 1 , 2, 1}, { 1, 3, 4, 0},
   * {5, 1, 6, 5}}
   * <p>
   * would get
   * </p>
   * 0 1 2 1 1 3 4 0 5 1 6 5
   *
   * @param data the 2D array containing the matrix data
   */
  public DoubleMatrix(double[][] data) {
    this.height = data.length;
    this.width = data[0].length;
    this.data = Arrays.stream(data).flatMapToDouble(Arrays::stream).toArray();
  }

  /**
   * Construct a new DoubleMatrix of the given size, initialized to 0.
   *
   * @param width the width of the matrix
   * @oaram height the height of the matrix
   * @return the new DoubleMatrix
   */
  public static DoubleMatrix zeroes(int width, int height) {
    double[][] data = new double[height][width];
    for (double[] datum : data) {
      Arrays.fill(datum, 0);
    }
    return new DoubleMatrix(data);
  }

  /**
   * Construct a square DoubleMatrix of 0.
   *
   * @param dimension the dimension of the matrix
   * @return the created DoubleMatrix
   */
  public static DoubleMatrix zeroes(int dimension) {
    return zeroes(dimension, dimension);
  }

  /**
   * Construct and identity matrix with a given height and width.
   *
   * @param width the width of the matrix
   * @param height the height of the matrix
   * @return the newly created identity matrix
   */
  public static DoubleMatrix identity(int width, int height) {
    double[][] data = new double[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        data[i][j] = j == i ? 1 : 0;
      }
    }
    return new DoubleMatrix(data);
  }

  /**
   * Construct a square identity matrix with the given dimension.
   *
   * @param dimensions the dimensions of the matrix
   * @return the new square identity matrix
   */
  public static DoubleMatrix identity(int dimensions) {
    return identity(dimensions, dimensions);
  }

  /**
   * Get the internal 1D representation of the matrix.
   *
   * @return the 1D array representation
   */
  public double[] getArray() {
    return data;
  }

  /**
   * Get a 2D representation of the matrix.
   *
   * @return a constructed 2D array representing the matrix
   */
  public double[][] get2dArray() {
    double[][] d2data = new double[height][width];
    for (int i = 0, z = 0; z < height; i++) {
      for (int j = 0; j < width; j++, z++) {
        d2data[i][j] = data[z];
      }
    }
    return d2data;
  }

  /**
   * Get a value from the matrix based on x and y position of the value.
   *
   * @param x the x coordinate of the value
   * @param y the y coordinate of the value
   * @return the value at the given coordinate pair
   */
  public double get(int x, int y) {
    if (x >= width || y >= height) {
      throw new ArrayIndexOutOfBoundsException();
    }
    return data[x + y * width];
  }

  /**
   * Get a value from the matrix based on its internal data position.
   *
   * @param i the index at which the value is located
   * @return the value at the given index
   */
  public double get(int i) {
    return data[i];
  }

  /**
   * Set a value in the matrix based on the x and y position of that value.
   *
   * @param x the x position of the value
   * @param y the y position of the value
   * @param value the new value for that position
   */
  public void set(int x, int y, double value) {
    if (x >= width || y >= height) {
      throw new ArrayIndexOutOfBoundsException();
    }
    data[x + y * width] = value;
  }

  /**
   * Set a value in the matrix based on its internal data position.
   *
   * @param i the index of the value
   * @param value the new value
   */
  public void set(int i, double value) {
    data[i] = value;
  }

  /**
   * Retrieve a row of the matrix as a 1D array of doubles.
   */
}
