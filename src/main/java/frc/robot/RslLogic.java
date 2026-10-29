package frc.robot;

import frc.robot.Constants.SignalLightConstants;

/**
 * Decides whether the robot signal light (RSL) should be lit right now.
 *
 * <p>This class has no hardware in it. It is pure logic: numbers and booleans in, a boolean out.
 * That is what makes it easy to test.
 */
public final class RslLogic {
  private RslLogic() {}

  /**
   * The rule for a real RSL: solid on while the robot is disabled, blinking while it is enabled.
   * While enabled, the light is on for the first half of every {@link
   * SignalLightConstants#kBlinkPeriodSeconds} and off for the second half.
   *
   * @param enabled true when the robot is enabled
   * @param timeSeconds the current time in seconds (where it started counting does not matter)
   * @return true when the light should be on
   */
  public static boolean shouldBeOn(boolean enabled, double timeSeconds) {
    if (!enabled) {
      return true; // solid on while disabled
    }
    // How far are we into the current blink cycle?
    double secondsIntoCycle = timeSeconds % SignalLightConstants.kBlinkPeriodSeconds;
    // On for the first half of the cycle, off for the second half.
    return secondsIntoCycle < SignalLightConstants.kBlinkPeriodSeconds / 2;
  }

  // Challenge 3 code:
  public static void RedLogic(boolean enabled, double timeSeconds) {
    if (enabled) {
      SignalLight.setRed(false); // solid off while enabled
    } else {
      // How far are we into the current blink cycle?
      int unitsIntoCycle = (int)((timeSeconds/SignalLightConstants.kSecondsPerUnit) % SignalLightConstants.kUnitPeriodSeconds);
      if (unitsIntoCycle == 1 || unitsIntoCycle == 3 || unitsIntoCycle == 5 || unitsIntoCycle = 9 || unitsIntoCycle = 10 || unitsIntoCycle = 11 || unitsIntoCycle = 13 || unitsIntoCycle = 14 || unitsIntoCycle = 15 || unitsIntoCycle = 17 || unitsIntoCycle = 18 || unitsIntoCycle = 19  || unitsIntoCycle = 23 || unitsIntoCycle = 25 || unitsIntoCycle = 27) {
        SignalLight.setRed(true);
      } else SignalLight.setRed(false);
    }
  }
}
