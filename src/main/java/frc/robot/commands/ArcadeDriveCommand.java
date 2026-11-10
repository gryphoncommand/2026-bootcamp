package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.DriveInput;
import frc.robot.subsystems.RomiDrivetrain;
import java.util.function.DoubleSupplier;

/** Drives the Romi from two controller sticks for as long as the command is scheduled. */
public class ArcadeDriveCommand extends Command {
  private final RomiDrivetrain m_drivetrain;
  private final DoubleSupplier m_forwardAxis;
  private final DoubleSupplier m_rotationAxis;

  /**
   * Creates the command.
   *
   * @param drivetrain the drivetrain to drive
   * @param forwardAxis where to read the forward/back stick value each loop
   * @param rotationAxis where to read the left/right stick value each loop
   */
  public ArcadeDriveCommand(
      RomiDrivetrain drivetrain, DoubleSupplier forwardAxis, DoubleSupplier rotationAxis) {
    m_drivetrain = drivetrain;
    m_forwardAxis = forwardAxis;
    m_rotationAxis = rotationAxis;
    // Only one command may use the drivetrain at a time.
    addRequirements(drivetrain);
  }

  /** Runs every 20 ms while scheduled: read the sticks, drive, and report what we did. */
  @Override
  public void execute() {
    double forward = DriveInput.forwardSpeed(m_forwardAxis.getAsDouble());
    double rotation = DriveInput.rotationSpeed(m_rotationAxis.getAsDouble());
    m_drivetrain.arcadeDrive(forward, rotation);

    SmartDashboard.putNumber("Drive/forward", forward);
    SmartDashboard.putNumber("Drive/rotation", rotation);
  }

  /** Runs once when the command stops, for any reason. Never leave the motors running. */
  @Override
  public void end(boolean interrupted) {
    m_drivetrain.arcadeDrive(0.0, 0.0);
  }

  /** Driving never finishes on its own. Another command interrupts it. */
  @Override
  public boolean isFinished() {
    return false;
  }
}
