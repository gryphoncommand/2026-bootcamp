package frc.robot;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Checks the SOS pattern at the middle of a few units. With a 0.25 s unit, every time here is
 * exact in binary, so the remainder math has no rounding surprises.
 */
class MorseLogicTest {
  @Test
  void firstSIsThreeDots() {
    assertTrue(MorseLogic.sosShouldBeOn(0.125)); // unit 0.5: first dot
    assertFalse(MorseLogic.sosShouldBeOn(0.375)); // unit 1.5: gap
    assertTrue(MorseLogic.sosShouldBeOn(0.625)); // unit 2.5: second dot
    assertTrue(MorseLogic.sosShouldBeOn(1.125)); // unit 4.5: third dot
  }

  @Test
  void lettersAreThreeUnitsApart() {
    assertFalse(MorseLogic.sosShouldBeOn(1.375)); // unit 5.5
    assertFalse(MorseLogic.sosShouldBeOn(1.875)); // unit 7.5
  }

  @Test
  void oIsThreeDashes() {
    assertTrue(MorseLogic.sosShouldBeOn(2.125)); // unit 8.5: first dash starts
    assertTrue(MorseLogic.sosShouldBeOn(2.625)); // unit 10.5: still the first dash
    assertFalse(MorseLogic.sosShouldBeOn(2.875)); // unit 11.5: gap
    assertTrue(MorseLogic.sosShouldBeOn(4.125)); // unit 16.5: third dash
  }

  @Test
  void secondSThenAWordGap() {
    assertTrue(MorseLogic.sosShouldBeOn(5.625)); // unit 22.5
    assertFalse(MorseLogic.sosShouldBeOn(7.625)); // unit 30.5: word gap
  }

  @Test
  void patternRepeats() {
    assertTrue(MorseLogic.sosShouldBeOn(8.625)); // 8.5 s later: unit 0.5 again
  }
}
