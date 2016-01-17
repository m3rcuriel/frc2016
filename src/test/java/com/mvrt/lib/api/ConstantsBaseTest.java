package com.mvrt.lib.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.mvrt.lib.api.ConstantsBase;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;



public class ConstantsBaseTest {
  public static class ConstantsTest extends ConstantsBase {
    public static int thingA = 5;
    public static double thingB = 5.115;

    public static double kEndEditableArea = 0;

    public static double thingC = new Double(12.4);

    @Override
    public String getFileLocation() {
      return System.getProperty("user.home") + File.separator + "constants_test.txt";
    }
  }

  @Test
  public void testKeyExtraction() {
    Collection<ConstantsBase.Constant> constants = (new ConstantsTest()).getConstants();
    ConstantsBase.Constant[] expected = new ConstantsBase.Constant[] {
        new ConstantsBase.Constant("thingA", int.class, 5),
        new ConstantsBase.Constant("thingB", double.class, 5.115)};

    assertTrue(Arrays.equals(expected, constants.toArray()));
  }

  @Test
  public void testCanSet() {
    int old = ConstantsTest.thingA;
    boolean set = new ConstantsTest().setConstant("thingA", 8);
    assertEquals(ConstantsTest.thingA, 8);
    assertTrue(set);
    set = new ConstantsTest().setConstant("thingA", old);
    assertEquals(ConstantsTest.thingA, old);
    assertTrue(set);
  }

  @Test
  public void testFailOnType() {
    int old = ConstantsTest.thingA;
    boolean set = new ConstantsTest().setConstant("thingA", 1.15);
    assertFalse(set);
    assertEquals(ConstantsTest.thingA, 5);
    set = new ConstantsTest().setConstant("thingA", old);
    assertEquals(ConstantsTest.thingA, old);
    assertTrue(set);
  }

  @Test
  public void testReadFile() {
    int old = ConstantsTest.thingA;
    JSONObject j = new JSONObject();
    j.put("thingA", 123);
    File f = new ConstantsTest().getFile();
    try {
      FileWriter w = new FileWriter(f);
      w.write(j.toJSONString());
      w.close();
    } catch (IOException e) {
      assertTrue("couldn't write file", false);
    }

    new ConstantsTest().loadFromFile();

    assertEquals(ConstantsTest.thingA, 123);

    new ConstantsTest().setConstant("thingA", old);
    assertEquals(ConstantsTest.thingA, old);
  }

  @Test
  public void testWriteFile() {
    ConstantsTest t = new ConstantsTest();
    int oldA = ConstantsTest.thingA;
    double oldB = ConstantsTest.thingB;

    t.setConstant("thingB", 987.76);
    t.setConstant("thingA", 1234);

    t.saveToFile();

    try {
      JSONObject j = t.getJsonObjectFromFile();
      assertEquals(j.get("thingB"), 987.76);
      assertEquals(j.get("thingA"), 1234L);
    } catch (IOException | ParseException e) {
      e.printStackTrace();
      assertTrue("couldn't get file jsonobject", false);
    }

    t.setConstant("thingA", oldA);
    t.setConstant("thingB", oldB);
  }
}

