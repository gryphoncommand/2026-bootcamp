// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import frc.robot.Constants.DriveConstants;

/** The Romi's two drive motors and two wheel encoders. */
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

  private XboxController m_drivecontroller = new XboxController(0);

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

  @Override
  public void periodic() {
    arcadeDrive(m_drivecontroller.getLeftY(), m_drivecontroller.getLeftX());
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
  }
}
