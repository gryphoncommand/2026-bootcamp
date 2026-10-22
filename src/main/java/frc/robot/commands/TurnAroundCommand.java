package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.RomiDrivetrain;

/**
 * Spins the Romi in place for a fixed time, then stops. Finishes on its own.
 *
 * <p>There is no gyro yet, so "180 degrees" is really "turn at this speed for this long". Both
 * numbers live in {@link DriveConstants} so they can be tuned on the real Romi without touching
 * this file.
 */
public class TurnAroundCommand extends Command {
  private final RomiDrivetrain m_drivetrain;
  private final Timer m_timer = new Timer();

  public TurnAroundCommand(RomiDrivetrain drivetrain) {
    m_drivetrain = drivetrain;
    addRequirements(drivetrain);
  }

  /** Runs once each time the command starts: start the clock from zero. */
  @Override
  public void initialize() {
    m_timer.restart();
  }

  /** Runs every 20 ms: keep turning. The drivetrain must be told every loop. */
  @Override
  public void execute() {
    m_drivetrain.arcadeDrive(0.0, DriveConstants.kTurnAroundSpeed);
  }

  /** Runs once when the command stops: never leave the motors running. */
  @Override
  public void end(boolean interrupted) {
    m_drivetrain.arcadeDrive(0.0, 0.0);
  }

  /** Done when the clock says so. */
  @Override
  public boolean isFinished() {
    return m_timer.hasElapsed(DriveConstants.kTurnAroundSeconds);
  }
}
