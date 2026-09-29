// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.romi.RomiGyro;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

/** The Romi's two drive motors, two wheel encoders, and gyro. */
public class RomiDrivetrain extends SubsystemBase implements AutoCloseable {
  // The Romi's motors are on PWM channels 0 and 1.
  private final Spark m_leftMotor = new Spark(DriveConstants.kLeftMotorChannel);
  private final Spark m_rightMotor = new Spark(DriveConstants.kRightMotorChannel);

  // The Romi's encoders are wired to DIO pins 4/5 (left) and 6/7 (right).
  private final Encoder m_leftEncoder =
      new Encoder(DriveConstants.kLeftEncoderChannelA, DriveConstants.kLeftEncoderChannelB);
  private final Encoder m_rightEncoder =
      new Encoder(DriveConstants.kRightEncoderChannelA, DriveConstants.kRightEncoderChannelB);

  // Turns "forward" and "turn" speeds into a speed for each side.
  private final DifferentialDrive m_diffDrive =
      new DifferentialDrive(m_leftMotor::set, m_rightMotor::set);

  // The gyro on the Romi's control board measures how far the robot has turned.
  private final RomiGyro m_gyro = new RomiGyro();

  // Odometry adds up wheel travel and heading to estimate where the robot is.
  private final DifferentialDriveOdometry m_odometry =
      new DifferentialDriveOdometry(getRotation(), 0.0, 0.0);

  // A drawing of the field with the robot on it, published to the dashboard.
  private final Field2d m_field = new Field2d();

  /** Creates a new RomiDrivetrain. */
  public RomiDrivetrain() {
    // One encoder count is this many inches of wheel travel.
    double inchesPerCount =
        (Math.PI * DriveConstants.kWheelDiameterInch) / DriveConstants.kCountsPerRevolution;
    m_leftEncoder.setDistancePerPulse(inchesPerCount);
    m_rightEncoder.setDistancePerPulse(inchesPerCount);
    resetEncoders();

    // The right motor is mounted mirrored, so flip it.
    m_rightMotor.setInverted(true);

    SmartDashboard.putData("Field", m_field);
  }

  /**
   * Drives the robot.
   *
   * @param xaxisSpeed forward speed, -1.0 to 1.0
   * @param zaxisRotate rotation speed, -1.0 to 1.0, counterclockwise positive
   */
  public void arcadeDrive(double xaxisSpeed, double zaxisRotate) {
    m_diffDrive.arcadeDrive(xaxisSpeed, zaxisRotate);
  }

  public void resetEncoders() {
    m_leftEncoder.reset();
    m_rightEncoder.reset();
  }

  public double getLeftDistanceInch() {
    return m_leftEncoder.getDistance();
  }

  public double getRightDistanceInch() {
    return m_rightEncoder.getDistance();
  }

  /** Average of both wheels: how far the robot has driven straight ahead. */
  public double getAverageDistanceInch() {
    return (getLeftDistanceInch() + getRightDistanceInch()) / 2.0;
  }

  /** Heading in degrees, straight from the gyro. Increases as the robot turns. */
  public double getGyroAngleDegrees() {
    return m_gyro.getAngleZ();
  }

  public void resetGyro() {
    m_gyro.reset();
  }

  /** Heading as a WPILib rotation, counterclockwise positive. */
  public Rotation2d getRotation() {
    double degrees = m_gyro.getAngleZ();
    if (DriveConstants.kGyroReversed) {
      degrees = -degrees;
    }
    return Rotation2d.fromDegrees(degrees);
  }

  /** Where odometry thinks the robot is, in meters, relative to where it started. */
  public Pose2d getPose() {
    return m_odometry.getPoseMeters();
  }

  @Override
  public void periodic() {
    // Feed odometry the latest heading and wheel distances (in meters, as WPILib expects).
    m_odometry.update(
        getRotation(),
        Units.inchesToMeters(getLeftDistanceInch()),
        Units.inchesToMeters(getRightDistanceInch()));
    m_field.setRobotPose(m_odometry.getPoseMeters());

    // Telemetry: numbers worth plotting go to NetworkTables, not the text log.
    SmartDashboard.putNumber("Drive/leftInch", getLeftDistanceInch());
    SmartDashboard.putNumber("Drive/rightInch", getRightDistanceInch());
    SmartDashboard.putNumber("Drive/headingDeg", getGyroAngleDegrees());
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

  /** Frees the hardware channels. Unit tests call this so each test starts clean. */
  @Override
  public void close() {
    m_diffDrive.close();
    m_leftMotor.close();
    m_rightMotor.close();
    m_leftEncoder.close();
    m_rightEncoder.close();
    m_gyro.close();
    m_field.close();
  }
}
