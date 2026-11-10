// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * One place for every number the robot depends on. Each nested class groups the constants for one
 * part of the robot. Nothing in here does anything; it only holds values.
 */
public final class Constants {
  private Constants() {}

  /** Which USB port the driver's controller is plugged into. */
  public static final class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }

  /** Numbers that describe the Romi's drivetrain hardware. */
  public static final class DriveConstants {
    public static final int kLeftMotorChannel = 0;
    public static final int kRightMotorChannel = 1;
    public static final int kLeftEncoderChannelA = 4;
    public static final int kLeftEncoderChannelB = 5;
    public static final int kRightEncoderChannelA = 6;
    public static final int kRightEncoderChannelB = 7;

    /** Encoder counts for one full turn of a wheel. */
    public static final double kCountsPerRevolution = 1440.0;

    /** The Romi's wheels are 70 mm across. */
    public static final double kWheelDiameterInch = 2.75591;

    /** Stick values closer to center than this count as zero. */
    public static final double kDeadband = 0.1;

    /** Speed multiplier while the slow-mode button is held. */
    public static final double kSlowModeFactor = 0.5;

    /**
     * WPILib counts counterclockwise turns as positive. If the robot on the field drawing turns
     * the opposite way from the real Romi, set this to true.
     */
    public static final boolean kGyroReversed = false;
  }

  /** Numbers for the robot signal light (RSL). */
  public static final class SignalLightConstants {
    /** One full on-then-off blink takes this many seconds while the robot is enabled. */
    public static final double kBlinkPeriodSeconds = 1.0;
  }
}
