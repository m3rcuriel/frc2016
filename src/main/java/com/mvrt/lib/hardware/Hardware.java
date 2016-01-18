package com.mvrt.lib.hardware;

import com.mvrt.lib.components.Motor;
import com.mvrt.lib.components.SimpleAccumulatedSensor;
import com.mvrt.lib.components.oi.FlightStick;
import com.mvrt.lib.components.oi.InputDevice;
import com.mvrt.lib.util.Values;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;

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
          .create(joystick::getRawAxis, joystick::getRawButton, joystick::getPOV, joystick::getY,
              () -> joystick.getTwist() * -1, joystick::getX, joystick::getThrottle,
              () -> joystick.getRawButton(1), () -> joystick.getRawButton(2));
    }
  }

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
}
