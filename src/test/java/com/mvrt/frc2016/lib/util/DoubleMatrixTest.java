package com.mvrt.frc2016.lib.util;

import static org.junit.Assert.assertTrue;
import com.mvrt.lib.util.DoubleMatrix;
import org.junit.Test;

import java.util.Arrays;

public class DoubleMatrixTest {

  private DoubleMatrix matrix;

  @Test
  public void testString() {
    double[][] data = new double[][] {{0, 1, 2}, {4, 5, 3}};
    matrix = new DoubleMatrix(data);
    assertTrue(matrix.toString().equals("2 * 3 matrix\n0.0\t1.0\t2.0\n4.0\t5.0\t3.0"));
  }

  @Test
  public void testEquals() {
    double[][] data = new double[][] {{0, 1, 2}, {4, 5, 3}};
    matrix = new DoubleMatrix(data);
    assertTrue(matrix.equals(matrix));
    DoubleMatrix matrix2 = new DoubleMatrix(data);
    assertTrue(matrix.equals(matrix2));
  }

  @Test
  public void testd1() {
    double[][] data = new double[][] {{0, 1, 2}, {4, 5, 3}};
    double[] flatData = Arrays.stream(data).flatMapToDouble(Arrays::stream).toArray();
    matrix = new DoubleMatrix(data);
    assertTrue(Arrays.equals(flatData, matrix.getArray()));
    assertTrue(matrix.getArray()[1 + (3 * 1)] == 5);
  }

  @Test
  public void testd2() {
    double[][] data = new double[][] {{0, 1, 2}, {4, 5, 3}};

    matrix = new DoubleMatrix(data);
    assertTrue(Arrays.deepEquals(data, matrix.get2dArray()));
    assertTrue(matrix.get2dArray()[1][2] == 3);
  }

  @Test
  public void testIdentity() {
    double[][] result = new double[][] {{1, 0, 0}, {0, 1, 0}, {0, 0, 1}, {0, 0, 0}};
    assertTrue(Arrays.deepEquals(result, DoubleMatrix.identity(3, 4).get2dArray()));
  }

  @Test
  public void testGet() {
    double[][] data = new double[][] {{0, 1, 2}, {4, 5, 3}};
    matrix = new DoubleMatrix(data);
    assertTrue(matrix.get(4) == 5);
    assertTrue(matrix.get(2, 1) == 3);
    assertTrue(Arrays.equals(matrix.getRow(1), new double[] {4, 5, 3}));
    assertTrue(Arrays.equals(matrix.getColumn(0), new double[] {0, 4}));
  }

  @Test
  public void testSet() {
    double[][] data = new double[][] {{0, 1, 5}, {0, 7, 9}, {1, 4, 6}};
    matrix = new DoubleMatrix(data);

    matrix.set(1, 0, 2);
    assertTrue(matrix.get(1, 0) == 2);
    matrix.set(3, 2);
    assertTrue(matrix.get(0, 1) == 2);
  }

  @Test
  public void testFlash() {
    double[][] data = new double[][] {{0, 1}, {0, 100}};
    double[][] newData = new double[][] {{1, 100}, {4, 5}};

    matrix = new DoubleMatrix(data);
    DoubleMatrix compare = new DoubleMatrix(newData);

    assertTrue(Arrays.deepEquals(matrix.get2dArray(), data));
    matrix.flash(newData);
    assertTrue(matrix.equals(compare));
  }

  @Test
  public void testClone() {
    double[][] data = new double[][] {{0, 1, 5}, {0, 7, 9}, {1, 4, 6}};
    matrix = new DoubleMatrix(data);
    DoubleMatrix matrix2 = matrix.clone();
    assertTrue(Arrays.equals(matrix.getArray(), matrix2.getArray()));
  }

  @Test
  public void testArithmatic() {
    // add subtract
    double[][] data = new double[][] {{0, 1, 5}, {0, 7, 9}, {1, 4, 6}};
    matrix = new DoubleMatrix(data);
    double[][] data2 = new double[][] {{0, 1, 2}, {4, 5, 3}, {3, 7, 9}};
    DoubleMatrix matrix2 = new DoubleMatrix(data2);
    double[][] result = new double[][] {{0, 2, 7}, {4, 12, 12}, {4, 11, 15}};
    assertTrue(Arrays.deepEquals(DoubleMatrix.add(matrix, matrix2).get2dArray(), result));
    result = new double[][] {{0, 0, -3}, {4, -2, -6}, {2, 3, 3}};
    assertTrue(Arrays.deepEquals(DoubleMatrix.subtract(matrix2, matrix).get2dArray(), result));

    // multiplication
    double[][] data1 = new double[][] {{0, 1}, {3, 4}};
    data2 = new double[][] {{1}, {2}};
    double[] resultm = new double[] {2, 11};
    DoubleMatrix mat1 = new DoubleMatrix(data1);
    DoubleMatrix mat2 = new DoubleMatrix(data2);
    assertTrue(Arrays.equals(DoubleMatrix.multiply(mat1, mat2).getArray(), resultm));
  }

  @Test
  public void testDeterminant() {
    double[][] data = new double[][] {{6, 1, 1}, {4, -2, 5}, {2, 8, 7}};
    matrix = new DoubleMatrix(data);

    assertTrue(matrix.determinant() == -306);
  }
}
