// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.ArcadeDriveCommand;
import frc.robot.subsystems.RomiDrivetrain;
import frc.robot.subsystems.SignalLight;

/**
 * This class is where the robot is described: its subsystems, its commands, and which controller
 * buttons run which commands. The {@link Robot} class only runs the scheduler.
 */
public class RobotContainer {
  // The robot's subsystems. Each one is an object built from its class.
  private final RomiDrivetrain m_drivetrain = new RomiDrivetrain();
  private final SignalLight m_signalLight = new SignalLight();

  // The driver's controller, plugged into USB port 0 on the Driver Station.
  private final CommandXboxController m_controller =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);

  /** The container for the robot. Contains subsystems, controllers, and commands. */
  public RobotContainer() {
    configureButtonBindings();
  }

  /** Controller sticks and buttons become commands here. */
  private void configureButtonBindings() {
    // Default: drive from the sticks whenever nothing else needs the drivetrain.
    m_drivetrain.setDefaultCommand(
        new ArcadeDriveCommand(m_drivetrain, m_controller::getLeftY, m_controller::getRightX));

    // Hold the right bumper for half speed. While held, this command replaces the default one.
    m_controller
        .rightBumper()
        .whileTrue(
            new ArcadeDriveCommand(
                m_drivetrain,
                () -> m_controller.getLeftY() * DriveConstants.kSlowModeFactor,
                () -> m_controller.getRightX() * DriveConstants.kSlowModeFactor));

    // Hold A on the controller to light the green LED on the Romi.
    m_controller
        .a()
        .whileTrue(
            m_signalLight.startEnd(
                () -> m_signalLight.setGreen(true), () -> m_signalLight.setGreen(false)));

    // Pressing button A on the Romi's control board prints a message.
    new Trigger(m_signalLight::isButtonAPressed).onTrue(Commands.print("Romi button A pressed"));
  }

  /**
   * The command to run in autonomous. Session 6 replaces this with a chooser on the dashboard.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return Commands.print("No autonomous yet");
  }
}
