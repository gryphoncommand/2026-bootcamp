// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.ArcadeDriveCommand;
import frc.robot.commands.DriveDistance;
import frc.robot.commands.TurnDegrees;
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

  // The list of autonomous routines the driver picks from on the dashboard.
  private final SendableChooser<Command> m_autoChooser = new SendableChooser<>();

  /** The container for the robot. Contains subsystems, controllers, and commands. */
  public RobotContainer() {
    configureButtonBindings();
    configureAutoChooser();
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

  /** Every autonomous routine goes in the chooser. The first one is the default. */
  private void configureAutoChooser() {
    m_autoChooser.setDefaultOption("Do nothing", Commands.none());
    m_autoChooser.addOption("Drive 12 inches", new DriveDistance(0.5, 12.0, m_drivetrain));
    m_autoChooser.addOption(
        "L shape",
        Commands.sequence(
            new DriveDistance(0.5, 12.0, m_drivetrain),
            new TurnDegrees(0.5, 90.0, m_drivetrain),
            new DriveDistance(0.5, 12.0, m_drivetrain)));
    SmartDashboard.putData("Auto", m_autoChooser);
  }

  /**
   * The routine the driver selected on the dashboard. {@link Robot#autonomousInit()} schedules it.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return m_autoChooser.getSelected();
  }
}
