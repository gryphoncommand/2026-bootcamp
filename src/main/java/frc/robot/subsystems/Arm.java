package frc.robot.subsystems;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ArmConstants;

/**
 * The Romi arm kit: three servos. One lifts the arm, one tilts the wrist, one opens and closes
 * the gripper.
 *
 * <p>A servo is told a position from 0.0 to 1.0 and goes there on its own. There is no sensor to
 * read back, so the subsystem remembers what it last asked for.
 */
public class Arm extends SubsystemBase implements AutoCloseable {
  private final Servo m_lift = new Servo(ArmConstants.kLiftServoChannel);
  private final Servo m_tilt = new Servo(ArmConstants.kTiltServoChannel);
  private final Servo m_gripper = new Servo(ArmConstants.kGripperServoChannel);

  /** Creates the arm and moves it to a safe starting pose. */
  public Arm() {
    setLift(ArmConstants.kLiftDown);
    setTilt(ArmConstants.kTiltLevel);
    setGripper(ArmConstants.kGripperOpen);
  }

  /** Lift position, 0.0 to 1.0. Values outside that range are clamped so the servo never strains. */
  public void setLift(double position) {
    m_lift.set(MathUtil.clamp(position, 0.0, 1.0));
  }

  public void setTilt(double position) {
    m_tilt.set(MathUtil.clamp(position, 0.0, 1.0));
  }

  public void setGripper(double position) {
    m_gripper.set(MathUtil.clamp(position, 0.0, 1.0));
  }

  public double getLift() {
    return m_lift.get();
  }

  public double getTilt() {
    return m_tilt.get();
  }

  public double getGripper() {
    return m_gripper.get();
  }

  /** Moves the lift a little from where it is. Called every loop while a button is held. */
  public void nudgeLift(double delta) {
    setLift(getLift() + delta);
  }

  public void nudgeTilt(double delta) {
    setTilt(getTilt() + delta);
  }

  // Small commands other code can schedule. Each one finishes right away; the servo keeps moving.

  public Command liftTo(double position) {
    return runOnce(() -> setLift(position));
  }

  public Command tiltTo(double position) {
    return runOnce(() -> setTilt(position));
  }

  public Command openGripper() {
    return runOnce(() -> setGripper(ArmConstants.kGripperOpen));
  }

  public Command closeGripper() {
    return runOnce(() -> setGripper(ArmConstants.kGripperClosed));
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Arm/lift", getLift());
    SmartDashboard.putNumber("Arm/tilt", getTilt());
    SmartDashboard.putNumber("Arm/gripper", getGripper());
  }

  /** Frees the PWM channels. Unit tests call this so each test starts clean. */
  @Override
  public void close() {
    m_lift.close();
    m_tilt.close();
    m_gripper.close();
  }
}
