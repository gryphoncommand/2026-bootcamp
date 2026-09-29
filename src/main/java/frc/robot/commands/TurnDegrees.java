package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.RomiDrivetrain;

/** Turns in place by a number of degrees, using the gyro. Finishes on its own. */
public class TurnDegrees extends Command {
  private final RomiDrivetrain m_drivetrain;
  private final double m_speed;
  private final double m_degrees;

  // The heading when the command started.
  private double m_startDegrees;

  /**
   * Creates the command.
   *
   * @param speed turning speed, 0.0 to 1.0
   * @param degrees how far to turn; positive is counterclockwise (left), negative is clockwise
   * @param drivetrain the drivetrain to turn
   */
  public TurnDegrees(double speed, double degrees, RomiDrivetrain drivetrain) {
    m_speed = speed;
    m_degrees = degrees;
    m_drivetrain = drivetrain;
    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {
    m_startDegrees = m_drivetrain.getGyroAngleDegrees();
  }

  @Override
  public void execute() {
    double rotation = m_speed;
    if (m_degrees < 0) {
      rotation = -m_speed; // turn the other way
    }
    m_drivetrain.arcadeDrive(0.0, rotation);
  }

  @Override
  public void end(boolean interrupted) {
    m_drivetrain.arcadeDrive(0.0, 0.0);
  }

  /** Done when the heading has changed by enough, in either direction. */
  @Override
  public boolean isFinished() {
    double turnedDegrees = m_drivetrain.getGyroAngleDegrees() - m_startDegrees;
    return Math.abs(turnedDegrees) >= Math.abs(m_degrees);
  }
}
