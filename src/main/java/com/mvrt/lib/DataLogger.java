package com.mvrt.lib;

import com.mvrt.lib.api.Runnable;
import com.mvrt.lib.components.Switch;
import com.mvrt.lib.util.MappedWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.IntSupplier;

/**
 * This simple logger allows you to register suppliers and then write them to a file. This should
 * later be abstractified, given more features, or moved to JNI.
 *
 * @author Lee Mracek
 */
public class DataLogger implements Runnable {
  public static final int WRITE_FREQUENCY = (int) (20.0); // milliseconds per write
  public static final int RUNNING_TIME = 200; // approximate number of seconds in a match

  private final List<IntSupplier> suppliers = new ArrayList<>();
  private final List<String> names = new ArrayList<>();

  private volatile boolean running = false;

  private DataWriter logger;

  private static long wrapped = 0;

  /**
   * Starts the DataLogger.
   * <br>
   * Has no effect if the logger is already running.
   */
  public void startup() {
    if (running) {
      return;
    }
    running = true;

    logger = new DataWriter(names, suppliers);
  }

  @Override
  public void run(long timeInMillis, TimeUnit unit) {
    if (running) {
      logger.write(unit.convert(timeInMillis, TimeUnit.MILLISECONDS));
    } else {
      logger.close();
    }
  }

  /**
   * Stops the DataLogger.
   * <br>
   * Has no effect if the DataLogger is already stopped.
   */
  public void shutdown() {
    running = false;
  }

  /**
   * Register a new IntSupplier for periodic logging. Cast everything to int to reduce logging data
   * bandwidth.
   *
   * @param name the name of the data being logged
   * @param supplier the supplier of the data being logged
   */
  public void register(String name, IntSupplier supplier) {
    if (supplier == null) {
      throw new IllegalArgumentException("The supplier must be null!");
    }
    if (running) {
      throw new UnsupportedOperationException("The logger is already running; unable to register");
    }
    names.add(name);
    suppliers.add(supplier);
  }

  /**
   * Register a new Switch for periodic logging.
   *
   * @param name the name of the switch
   * @param swtch the switch
   */
  public void registerSwitch(String name, Switch swtch) {
    if (swtch == null) {
      throw new IllegalArgumentException("The switch may not be null!");
    }
    if (running) {
      throw new UnsupportedOperationException("The logger is already running; unable to register");
    }
    names.add(name);
    suppliers.add(() -> swtch.isTriggered() ? 1 : 0);
  }

  /**
   * Bitmask an array of booleans.
   *
   * @param values the booleans to be combined
   * @return the constructed short
   */
  public static short bitmask(boolean... values) {
    if (values.length > 15) {
      throw new IllegalArgumentException("Cannot combine more than 15 booleans");
    }
    short value = 0;
    for (int i = 0; i < values.length; i++) {
      value = (short) (value | ((values[i] ? 1 : 0) << i));
    }
    return value;
  }

  private static class DataWriter {
    private final List<IntSupplier> suppliers;
    private final List<String> names;

    private long recordLength;
    private MappedWriter writer;

    private long minSize = 0;

    public DataWriter(List<String> names, List<IntSupplier> suppliers) {
      this.suppliers = suppliers;
      this.names = names;
      try {
        int numWrites = (int) ((1.0 / WRITE_FREQUENCY) * (RUNNING_TIME * 1000));

        recordLength = Integer.BYTES;
        recordLength += (Short.BYTES * suppliers.size());

        minSize = numWrites * suppliers.size();

        writer = new MappedWriter("~/usb/data.log", minSize + 1024);

        initialize();

      } catch (IOException e) {
        System.err.println("Failed to load logfile");
        e.printStackTrace();
      }
    }

    private void initialize() {
      writer.write("log".getBytes());

      writer.write((byte) (suppliers.size() + 1));
      writer.write((byte) Integer.BYTES);

      for (IntSupplier supplier : suppliers) {
        assert supplier != null;
        writer.write((byte) Short.BYTES);
      }

      writer.write((byte) 4);
      writer.write(("Time").getBytes());

      for (String name : names) {
        writer.write((byte) name.length());
        writer.write(name.getBytes());
      }
    }

    public void write(long time) {
      if (writer.getRemaining() < recordLength) {
        System.err.println("Not enough space for all of next record, wrapping file");
        writer.close();
        try {
          this.writer = new MappedWriter("~/usb/data-" + wrapped + ".log", minSize + 1024);
          initialize();
        } catch (IOException e) {
          e.printStackTrace();
        }

        writer.writeInt((int) time);
        suppliers.forEach((supplier -> writer.writeShort((short) supplier.getAsInt())));
      }
    }

    public void close() {
      writer.close();
    }
  }
}
