package frc.robot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/** Checks the stick-to-speed math. Decimal numbers are compared within a tiny tolerance. */
@Disabled("Session 4: delete this line, then make these tests pass")
class DriveInputTest {
  static final double DELTA = 1e-9;

  @Test
  void pushingForwardIsPositive() {
    // An Xbox stick reads -1.0 when pushed all the way forward.
    assertEquals(1.0, DriveInput.forwardSpeed(-1.0), DELTA);
    assertEquals(0.5, DriveInput.forwardSpeed(-0.5), DELTA);
  }

  @Test
  void pullingBackIsNegative() {
    assertEquals(-1.0, DriveInput.forwardSpeed(1.0), DELTA);
  }

  @Test
  void smallStickWobbleIsIgnored() {
    assertEquals(0.0, DriveInput.applyDeadband(0.09), DELTA);
    assertEquals(0.0, DriveInput.forwardSpeed(0.05), DELTA);
    assertEquals(0.0, DriveInput.forwardSpeed(-0.05), DELTA);
  }

  @Test
  void valuesPastTheDeadbandPassThrough() {
    assertEquals(0.3, DriveInput.applyDeadband(0.3), DELTA);
    assertEquals(-0.3, DriveInput.applyDeadband(-0.3), DELTA);
  }

  @Test
  void pushingRightTurnsClockwise() {
    // WPILib counts counterclockwise as positive, so stick-right must come out negative.
    assertEquals(-1.0, DriveInput.rotationSpeed(1.0), DELTA);
    assertEquals(0.0, DriveInput.rotationSpeed(0.05), DELTA);
  }
}
