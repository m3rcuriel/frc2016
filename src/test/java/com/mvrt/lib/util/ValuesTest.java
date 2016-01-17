package com.mvrt.lib.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.mvrt.lib.util.Values;
import org.junit.Test;

public class ValuesTest {

  @Test
  public void fuzzyCompareDefaultTest() {
    double inaccuracy = 3.0002;
    assertTrue(Values.fuzzyCompare(3, inaccuracy) == 0);
    inaccuracy = 3.0003;
    assertFalse(Values.fuzzyCompare(3, inaccuracy) == 0);
    assertTrue(Values.fuzzyCompare(3, inaccuracy) == -1);
    assertTrue(Values.fuzzyCompare(4, inaccuracy) == 1);
  }

  @Test
  public void fuzzyCompareBitTest() {
    double inaccuracy1 = 3.0425;
    double inaccuracy2 = 2.9374;
    double inaccuracy3 = 3.0626;

    assertTrue(Values.fuzzyCompare(3, inaccuracy1, 4) == 0);
    assertTrue(Values.fuzzyCompare(3, inaccuracy2, 4) == 1);
    assertTrue(Values.fuzzyCompare(3, inaccuracy3, 4) == -1);
  }
}
