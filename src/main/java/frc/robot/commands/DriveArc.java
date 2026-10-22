package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.RomiDrivetrain;

/**
 * Drives a curve: forward and turning at the same time, until the wheels have covered a distance.
 * Two of these with opposite rotation make an S.
 */
public class DriveArc extends Command {
  private final RomiDrivetrain m_drivetrain;
  private final double m_speed;
  private final double m_rotation;
  private final double m_distanceInch;

  private double m_startInch;

  public DriveArc(double speed, double rotation, double distanceInch, RomiDrivetrain drivetrain) {
    m_speed = speed;
    m_rotation = rotation;
    m_distanceInch = distanceInch;
    m_drivetrain = drivetrain;
    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {
    m_startInch = m_drivetrain.getAverageDistanceInch();
  }

  @Override
  public void execute() {
    m_drivetrain.arcadeDrive(m_speed, m_rotation);
  }

  @Override
  public void end(boolean interrupted) {
    m_drivetrain.arcadeDrive(0.0, 0.0);
  }

  @Override
  public boolean isFinished() {
    double traveledInch = m_drivetrain.getAverageDistanceInch() - m_startInch;
    return Math.abs(traveledInch) >= m_distanceInch;
  }
}
