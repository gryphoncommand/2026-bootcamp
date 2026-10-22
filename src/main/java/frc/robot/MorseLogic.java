package frc.robot;

import frc.robot.Constants.SignalLightConstants;

/**
 * Decides whether an LED flashing SOS in Morse code should be lit right now.
 *
 * <p>Pure logic, like {@link RslLogic}: a time in, a boolean out, no hardware. Morse code is
 * measured in units. A dot is one unit on, a dash is three units on. Dots and dashes inside a
 * letter are one unit apart, letters are three units apart, and words are seven units apart.
 */
public final class MorseLogic {
  private MorseLogic() {}

  /** One full SOS, with the gap before it repeats, is this many units long. */
  private static final int kPatternUnits = 34;

  /**
   * SOS is three dots, three dashes, three dots, then a word gap. Laid out in units:
   *
   * <pre>
   * S: dots start at units 0, 2, 4        (letter gap: units 5-7)
   * O: dashes start at units 8, 12, 16    (letter gap: units 19-21)
   * S: dots start at units 22, 24, 26     (word gap: units 27-33)
   * </pre>
   *
   * @param timeSeconds the current time in seconds (where it started counting does not matter)
   * @return true when the LED should be on
   */
  public static boolean sosShouldBeOn(double timeSeconds) {
    double periodSeconds = SignalLightConstants.kMorseUnitSeconds * kPatternUnits;
    // How many units into the current SOS are we? A decimal, from 0 up to 34.
    double unitsIn = (timeSeconds % periodSeconds) / SignalLightConstants.kMorseUnitSeconds;

    return dot(unitsIn, 0)
        || dot(unitsIn, 2)
        || dot(unitsIn, 4)
        || dash(unitsIn, 8)
        || dash(unitsIn, 12)
        || dash(unitsIn, 16)
        || dot(unitsIn, 22)
        || dot(unitsIn, 24)
        || dot(unitsIn, 26);
  }

  /** True while unitsIn is inside a one-unit dot that starts at startUnit. */
  private static boolean dot(double unitsIn, int startUnit) {
    return unitsIn >= startUnit && unitsIn < startUnit + 1;
  }

  /** True while unitsIn is inside a three-unit dash that starts at startUnit. */
  private static boolean dash(double unitsIn, int startUnit) {
    return unitsIn >= startUnit && unitsIn < startUnit + 3;
  }
}
