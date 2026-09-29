package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.RomiDrivetrain;

/** Drives straight for a set distance, then stops. Finishes on its own. */
public class DriveDistance extends Command {
  private final RomiDrivetrain m_drivetrain;
  private final double m_speed;
  private final double m_distanceInch;

  // Where the wheels were when the command started.
  private double m_startInch;

  /**
   * Creates the command.
   *
   * @param speed forward speed, 0.0 to 1.0
   * @param distanceInch how far to drive, in inches
   * @param drivetrain the drivetrain to drive
   */
  public DriveDistance(double speed, double distanceInch, RomiDrivetrain drivetrain) {
    m_speed = speed;
    m_distanceInch = distanceInch;
    m_drivetrain = drivetrain;
    addRequirements(drivetrain);
  }

  /** Runs once when the command starts: remember where we began. */
  @Override
  public void initialize() {
    m_startInch = m_drivetrain.getAverageDistanceInch();
  }

  /** Runs every 20 ms: keep driving. */
  @Override
  public void execute() {
    m_drivetrain.arcadeDrive(m_speed, 0.0);
  }

  /** Runs once when the command stops: never leave the motors running. */
  @Override
  public void end(boolean interrupted) {
    m_drivetrain.arcadeDrive(0.0, 0.0);
  }

  /** Done when the wheels have traveled far enough since we started. */
  @Override
  public boolean isFinished() {
    double traveledInch = m_drivetrain.getAverageDistanceInch() - m_startInch;
    return Math.abs(traveledInch) >= m_distanceInch;
  }
}
