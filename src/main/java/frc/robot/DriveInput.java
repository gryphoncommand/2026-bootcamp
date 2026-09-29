package frc.robot;

import frc.robot.Constants.DriveConstants;

/**
 * Turns raw controller stick values into drivetrain speeds.
 *
 * <p>Pure math, no hardware. A stick value is always between -1.0 and 1.0.
 */
public final class DriveInput {
  private DriveInput() {}

  /**
   * Ignores small stick wobble. Returns 0.0 when the stick is closer to center than {@link
   * DriveConstants#kDeadband}, otherwise returns the value unchanged.
   *
   * @param rawAxis the stick value straight from the controller
   * @return the value to use, with tiny wobble removed
   */
  public static double applyDeadband(double rawAxis) {
    // TODO Session 4: replace this line with the real rule.
    return 0.0;
  }

  /**
   * Forward speed from the left stick's Y axis. Pushing the stick forward reads -1.0 on an Xbox
   * controller, so the sign must flip: positive means forward.
   *
   * @param rawAxis the left stick's Y value
   * @return forward speed from -1.0 (full reverse) to 1.0 (full forward)
   */
  public static double forwardSpeed(double rawAxis) {
    // TODO Session 4: replace this line with the real rule.
    return 0.0;
  }

  /**
   * Rotation speed from the right stick's X axis. WPILib counts counterclockwise as positive, so
   * pushing the stick right (positive) must turn the robot clockwise (negative).
   *
   * @param rawAxis the right stick's X value
   * @return rotation speed from -1.0 (clockwise) to 1.0 (counterclockwise)
   */
  public static double rotationSpeed(double rawAxis) {
    // TODO Session 4: replace this line with the real rule.
    return 0.0;
  }
}
