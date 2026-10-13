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

  public ArcadeDriveCommand(
      RomiDrivetrain drivetrain, DoubleSupplier forwardAxis, DoubleSupplier rotationAxis) {
    m_drivetrain = drivetrain;
    m_forwardAxis = forwardAxis;
    m_rotationAxis = rotationAxis;
    addRequirements(drivetrain);
  }

  @Override
  public void execute() {
    double forward = DriveInput.forwardSpeed(m_forwardAxis.getAsDouble());
    double rotation = DriveInput.rotationSpeed(m_rotationAxis.getAsDouble());
    m_drivetrain.arcadeDrive(forward, rotation);
    SmartDashboard.putNumber("Drive/forward", forward);
    SmartDashboard.putNumber("Drive/rotation", rotation);
  }

  @Override
  public void end(boolean interrupted) {
    m_drivetrain.arcadeDrive(0.0, 0.0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
