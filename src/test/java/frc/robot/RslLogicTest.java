package frc.robot;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.jupiter.api.Test;

/** Checks the robot signal light rule. Each method is one test. */

class RslLogicTest {
  @Test
  void disabledRobotIsSolidOn() {
    assertTrue(RslLogic.shouldBeOn(false, 0.0));
    assertTrue(RslLogic.shouldBeOn(false, 0.75));
    assertTrue(RslLogic.shouldBeOn(false, 123.5));
  }

  @Test
  void enabledRobotIsOnDuringFirstHalfSecond() {
    assertTrue(RslLogic.shouldBeOn(true, 0.0));
    assertTrue(RslLogic.shouldBeOn(true, 0.25));
    assertTrue(RslLogic.shouldBeOn(true, 10.25));
  }

  @Test
  void enabledRobotIsOffDuringSecondHalfSecond() {
    assertFalse(RslLogic.shouldBeOn(true, 0.5));
    assertFalse(RslLogic.shouldBeOn(true, 0.75));
    assertFalse(RslLogic.shouldBeOn(true, 10.75));
  }
}
