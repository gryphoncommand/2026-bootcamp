package frc.robot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;
import org.junit.jupiter.api.Test;

/** Checks that messages turn into the right on/off pattern. 1 is light on, 0 is light off. */
class MorseLogicTest {
  @Test
  void sosEncodesToMorsePattern() {
    MorseLogic morse = new MorseLogic();

    List<Integer> expected =
        List.of(
            1, 0, 1, 0, 1, 0, 0, 0, // S, then the gap between letters
            1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, // O, then the gap between letters
            1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0); // S, then the gap after the word

    var response = morse.generateEncoding("SOS");
  
    assertEquals(expected, response);
  }

  /** How many loops one Morse unit lasts. Change this if the light's timing changes. */
  private static final int LOOPS_PER_UNIT = 40;

  @Test
  void lightFollowsSosPatternWhileDisabled() {
    MorseLogic morse = new MorseLogic("SOS");
    List<Integer> pattern = morse.generateEncoding("SOS");

    // Like periodic(), call it every loop and check the light the whole time.
    for (int loop = 0; loop < pattern.size() * LOOPS_PER_UNIT; loop++) {
      int unit = loop / LOOPS_PER_UNIT;
      boolean expectedOn = pattern.get(unit) == 1;
      assertEquals(expectedOn, morse.shouldBeOn(false, loop), "loop " + loop);
    }
  }

  @Test
  void patternRepeatsAfterTheMessageEnds() {
    MorseLogic morse = new MorseLogic("SOS");
    List<Integer> pattern = morse.generateEncoding("SOS");

    // Run through the message twice. The second time should match the first.
    for (int loop = 0; loop < pattern.size() * LOOPS_PER_UNIT * 2; loop++) {
      int unit = (loop / LOOPS_PER_UNIT) % pattern.size();
      boolean expectedOn = pattern.get(unit) == 1;
      assertEquals(expectedOn, morse.shouldBeOn(false, loop), "loop " + loop);
    }
  }

  @Test
  void lightIsOffWhileEnabled() {
    MorseLogic morse = new MorseLogic("SOS");

    // Loop 10 is during the first dot of S, when a disabled robot's light is on.
    assertFalse(morse.shouldBeOn(true, 10));
    assertFalse(morse.shouldBeOn(true, 500));
  }
}
