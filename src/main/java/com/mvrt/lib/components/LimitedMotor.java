package com.mvrt.lib.components;

import com.mvrt.lib.util.Values;
import javafx.geometry.Pos;

/**
 * A Motor that is bounded by two limit switches.
 * @author Ishan
 */
public interface LimitedMotor extends Motor{

  public enum Position {
    FORWARD_LIMIT,
    REVERSE_LIMIT,
    UNKNOWN
  }

  @Override
  LimitedMotor setSpeed(double speed);

  Switch getForwardLimitSwitch();

  Switch getReverseLimitSwitch();

  default boolean isAtForwardLimit() {
    return getForwardLimitSwitch().isTriggered();
  }

  default boolean isAtReverseLimit() {
    return getReverseLimitSwitch().isTriggered();
  }

  default boolean forward(double speed) {
    // Motor protection
    if (!isAtForwardLimit()) {
      setSpeed(Math.abs(speed));
    } else {
      stop();
    }
    return !isAtForwardLimit();
  }

  default boolean reverse(double speed) {
    // Motor protection
    if (!isAtReverseLimit()) {
      setSpeed(-Math.abs(speed));
    } else {
      stop();
    }
    return !isAtForwardLimit();
  }

  default Position getPosition() {
    switch (getDirection()) {
      case FORWARD:
      case REVERSE:
        return Position.UNKNOWN;
      case STOPPED:
        boolean fwdLimited = isAtForwardLimit();
        boolean revLimited = isAtReverseLimit();
        if (fwdLimited && !revLimited) {
          return Position.FORWARD_LIMIT;
        }
        if (revLimited && !fwdLimited) {
          return Position.REVERSE_LIMIT;
        }
        return Position.UNKNOWN;
      default:
        return Position.UNKNOWN;
    }
  }


  static LimitedMotor create(Motor motor, Switch forwardSwitch, Switch reverseSwitch) {
    if (motor == null) {
      throw new IllegalArgumentException("The motor may not be null");
    }
    return new LimitedMotor() {
      @Override
      public double getSpeed() {
        double speed = motor.getSpeed();
        int direction = Values.fuzzyCompare(speed, 0.0);
        if (direction > 0 && forwardSwitch.isTriggered()) {
          return 0.0;
        }
        if (direction < 0 && reverseSwitch.isTriggered()) {
          return 0.0;
        }
        return speed;
      }

      @Override
      public LimitedMotor setSpeed(double speed) {
        int direction = Values.fuzzyCompare(speed, 0.0);
        if (direction > 0 && !forwardSwitch.isTriggered()) {
          motor.setSpeed(speed);
        } else if (direction < 0 && !reverseSwitch.isTriggered()) {
          motor.setSpeed(speed);
        } else {
          motor.stop();
        }
        return this;
      }

      @Override
      public Switch getForwardLimitSwitch() {
        return forwardSwitch;
      }

      @Override
      public Switch getReverseLimitSwitch() {
        return reverseSwitch;
      }

      @Override
      public Motor.Direction getDirection() {
        Direction dir = motor.getDirection(); // uses getSpeed()
        switch (dir) {
          case FORWARD:
            if (forwardSwitch.isTriggered()) {
              return Direction.STOPPED;
            }
            break;
          case REVERSE:
            if (reverseSwitch.isTriggered()) {
              return Direction.STOPPED;
            }
            break;
          case STOPPED:
            break;
          default:
            break;
        }
        return dir;
      }

      @Override
      public void stop() {
        motor.stop();
      }

    };
  }
}