package com.mvrt.lib.hardware;

import com.kauailabs.navx.frc.AHRS;
import com.mvrt.lib.components.AngleSensor;
import com.mvrt.lib.components.DistanceSensor;
import com.mvrt.lib.components.Gyroscope;
import com.mvrt.lib.components.Motor;
import com.mvrt.lib.components.PneumaticsControlModule;
import com.mvrt.lib.components.PowerDistributionPanel;
import com.mvrt.lib.components.SimpleAccumulatedSensor;
import com.mvrt.lib.components.Solenoid;
import com.mvrt.lib.components.Switch;
import com.mvrt.lib.components.oi.FlightStick;
import com.mvrt.lib.components.oi.InputDevice;
import com.mvrt.lib.util.Values;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.AnalogTrigger;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Ultrasonic;

import java.util.function.DoubleFunction;

/**
 * Contains factory methods to create implementatoins corresponding to physical hardware.
 * <br>
 * Subsystems, etc, should not know how to obtain components. Instead, the components should
 * be passed in via constructors; the references to components can be immutable and final.
 * <br>
 * This means subsystems can be tested off-robot without hardware by passing mock components
 * into the subsystem constructors.
 */
public class Hardware {

  /**
   * Generator for a PDP.
   *
   * @return a created PDP
   */
  public static PowerDistributionPanel powerDistributionPanel() {
    edu.wpi.first.wpilibj.PowerDistributionPanel pdp =
        new edu.wpi.first.wpilibj.PowerDistributionPanel();
    return PowerDistributionPanel
        .create(pdp::getCurrent, pdp::getTotalCurrent, pdp::getVoltage, pdp::getTemperature);
  }

  /**
   * Generator for a PCM.
   *
   * @return a PCM without a specified canID
   */
  public static PneumaticsControlModule pneumaticsControlModule() {
    return new HardwarePneumaticsControlModule(new Compressor());
  }

  /**
   * Generator for a PCM.
   *
   * @param canId the canId
   * @return a PCM with a specified canID
   */
  public static PneumaticsControlModule pneumaticsControlModule(int canId) {
    return new HardwarePneumaticsControlModule(new Compressor(canId));
  }

  /**
   * Inner class to construct any interface devices.
   */
  public static final class HumanInterfaceDevices {

    /**
     * Create a driver station joystick for the given port.
     *
     * @param port the port for the joystick
     * @return the created {@link InputDevice}
     */
    public static InputDevice driverStationJoystick(int port) {
      Joystick joystick = new Joystick(port);
      return InputDevice.create(joystick::getRawAxis, joystick::getRawButton, joystick::getPOV);
    }

    /**
     * Create a Logitech Attack 3D Pro joystick for the given port.
     *
     * @param port the port for the joystick
     * @return the created {@link FlightStick}
     */
    public static FlightStick logitechAttack3dPro(int port) {
      Joystick joystick = new Joystick(port);
      return FlightStick
          .create(joystick::getRawAxis, joystick::getRawButton, joystick::getPOV, () -> joystick.getY() * -1,
              () -> joystick.getTwist() * -1, joystick::getX, joystick::getThrottle,
              () -> joystick.getRawButton(1), () -> joystick.getRawButton(2));
    }
  }


  /**
   * Inner class to construct any accumulated sensors.
   */
  public static final class AccumulatedSensors {

    public static SimpleAccumulatedSensor quadEncoder(int aChannel, int bChannel,
        double distancePerPulse) {
      Encoder encoder = new Encoder(aChannel, bChannel);
      encoder.setDistancePerPulse(distancePerPulse);
      return new SimpleAccumulatedSensor() {
        @Override
        public double getPosition() {
          return encoder.getDistance();
        }

        @Override
        public double getRate() {
          return encoder.getRate();
        }

        @Override
        public SimpleAccumulatedSensor zero() {
          encoder.reset();
          return this;
        }
      };
    }
  }


  /**
   * Inner class to construct any angle measuring sensors.
   */
  public static final class AngleSensors {

    /**
     * Generator for creating an Encoder.
     *
     * @param aChannel         digital inout channel a
     * @param bChannel         digital input channel b
     * @param distancePerPulse the distance per pulse
     * @return an encoder object
     */
    public static AngleSensor encoder(int aChannel, int bChannel, double distancePerPulse) {
      Encoder encoder = new Encoder(aChannel, bChannel);
      encoder.setDistancePerPulse(distancePerPulse);
      return AngleSensor.create(encoder::getDistance);
    }

    public static AngleSensor potentiometer(int channel, double fullVoltageRangeToDegrees) {
      return potentiometer(channel, fullVoltageRangeToDegrees, 0.0);
    }

    public static AngleSensor potentiometer(int channel, double fullVoltageRangeToDegrees,
        double offsetInDegrees) {
      AnalogPotentiometer pot =
          new AnalogPotentiometer(channel, fullVoltageRangeToDegrees, offsetInDegrees);
      return AngleSensor.create(pot::get);
    }
  }


  /**
   * Inner class to construct any distance measuring sensors.
   */
  public static final class DistanceSensors {

    /**
     * Generator for a digital ultrasonic sensor.
     *
     * @param pingChannel The digital output channel that sends the pulse to
     *                    initiate the sensor sending the ping.
     * @param echoChannel The digital input channel that receives the echo. The
     *                    length of time that the echo is high represents the round trip time
     *                    of the ping, and the distance.
     * @return a created digital ultrasonic sensor
     */
    public static DistanceSensor digitalUltrasonic(int pingChannel, int echoChannel) {
      Ultrasonic ultrasonic = new Ultrasonic(pingChannel, echoChannel);
      ultrasonic.setAutomaticMode(true);
      return DistanceSensor.create(ultrasonic::getRangeInches);
    }

    /**
     * Generator for an analogUltrasonic sensor.
     *
     * @param channel       The channel number to represent
     * @param voltsToInches use voltage to get distance
     * @return a created Distance Sensor
     */
    public static DistanceSensor analogUltrasonic(int channel, double voltsToInches) {
      AnalogInput sensor = new AnalogInput(channel);
      return DistanceSensor.create(() -> sensor.getVoltage() * voltsToInches);
    }

    /**
     * Generator for a potentiometer.
     *
     * @param channel                  The channel number to represent
     * @param fullVoltageRangeToInches convert the obtained voltage to inches
     * @return an analog potentiometer
     */
    public static DistanceSensor potentiometer(int channel, double fullVoltageRangeToInches) {
      return potentiometer(channel, fullVoltageRangeToInches, 0.0);
    }

    public static DistanceSensor potentiometer(int channel, double fullVoltageRangeToInches,
        double offsetInches) {
      AnalogPotentiometer pot =
          new AnalogPotentiometer(channel, fullVoltageRangeToInches, offsetInches);
      return DistanceSensor.create(pot::get);
    }
  }


  /**
   * Inner class to create all {@link Motor}s.
   */
  public static final class Motors {
    private static final DoubleFunction<Double> SPEED_LIMITER = Values.limiter(-1.0, 1.0);

    /**
     * Create a new TalonSRX without all the fancy SRX features.
     *
     * @param canId the CAN ID of the Talon
     * @return the newly created {@link Motor}
     */
    public static Motor talonSrxRaw(int canId) {
      return new HardwareMotor(new CANTalon(canId), SPEED_LIMITER);
    }
  }


  /**
   * Inner class to construct any angle measuring sensors.
   */
  public static final class Solenoids {

    /**
     * Generator for a double solenoid.
     *
     * @param extendChannel    The forward channel on the module to control
     * @param retractChannel   The reverse channel on the module to control
     * @param initialDirection which direction the solenoid is first in
     * @return a solenoid w/out a module
     */
    public static Solenoid doubleSolenoid(int extendChannel, int retractChannel,
        Solenoid.Direction initialDirection) {
      DoubleSolenoid solenoid = new DoubleSolenoid(extendChannel, retractChannel);
      return new HardwareDoubleSolenoid(solenoid, initialDirection);
    }

    /**
     * Generator for a double solenoid with a module number.
     *
     * @param module           module number of solenoid module
     * @param extendChannel    The forward channel on the module to control
     * @param retractChannel   The reverse channel on the module to control
     * @param initialDirection which direction the solenoid is first in
     * @return solenoid with module
     */
    public static Solenoid doubleSolenoid(int module, int extendChannel, int retractChannel,
        Solenoid.Direction initialDirection) {
      DoubleSolenoid solenoid = new DoubleSolenoid(module, extendChannel, retractChannel);
      return new HardwareDoubleSolenoid(solenoid, initialDirection);
    }
  }


  /**
   * Inner class to construct any switches.
   */
  public static final class Switches {

    /**
     * Switch that is normally closed.
     *
     * @param channel the DIO channel
     * @return return a normally closed switch
     */
    public static Switch normallyClosed(int channel) {
      DigitalInput input = new DigitalInput(channel);
      return () -> !input.get();
    }

    /**
     * Switch that is normally open.
     *
     * @param channel the DIO channel
     * @return return a normally open switch
     */
    public static Switch normallyOpen(int channel) {
      DigitalInput input = new DigitalInput(channel);
      return input::get;
    }

    public static enum AnalogOption {
      FILTERED, AVERAGED, NONE;
    }


    public static enum TriggerMode {
      IN_WINDOW, AVERAGED;
    }

    /**
     * Make an analog switch.
     *
     * @param channel      the DIO channel
     * @param lowerVoltage the lower bound voltage
     * @param upperVoltage upper bound voltage
     * @param option       the option
     * @param mode         the triggermode
     * @return an analog switch
     */
    public static Switch analog(int channel, double lowerVoltage, double upperVoltage,
        AnalogOption option, TriggerMode mode) {
      if (option == null) {
        throw new IllegalArgumentException("The analog option must not be null");
      }
      if (mode == null) {
        throw new IllegalArgumentException("The analog mode must not be null");
      }
      AnalogTrigger trigger = new AnalogTrigger(channel);
      trigger.setLimitsVoltage(lowerVoltage, upperVoltage);
      switch (option) {
        case AVERAGED:
          trigger.setAveraged(true);
          break;
        case FILTERED:
          trigger.setFiltered(true);
          break;
        default:
          break;
      }
      return mode == TriggerMode.AVERAGED ? trigger::getTriggerState : trigger::getInWindow;
    }
  }

  /**
   * Represent a NavX AHRS as a Gyroscope.
   *
   * @param ahrs the AHRS to cast
   * @return the a Gyroscope implementation
   */
  public static Gyroscope ahrsAsGyroscope(AHRS ahrs) {
    return new Gyroscope() {

      @Override
      public double getRate() {
        return ahrs.getRate();
      }

      @Override
      public double getAngle() {
        return ahrs.getAngle();
      }

      @Override
      public void zero() {
        ahrs.reset();
      }

      @Override
      public double getRawAngle() {
        return ahrs.getAngle();
      }
    };
  }
}
