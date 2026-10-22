package frc.robot.subsystems;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.wpi.first.hal.HAL;
import frc.robot.Constants.ArmConstants;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Checks the arm's position bookkeeping with simulated servos. */
class ArmTest {
  // Servo positions are stored as whole microseconds of pulse width, so allow a little rounding.
  static final double DELTA = 1e-3;

  Arm m_arm;

  @BeforeEach
  void setUp() {
    assertTrue(HAL.initialize(500, 0));
    m_arm = new Arm();
  }

  @AfterEach
  void tearDown() {
    m_arm.close();
  }

  @Test
  void startsInTheSafePose() {
    assertEquals(ArmConstants.kLiftDown, m_arm.getLift(), DELTA);
    assertEquals(ArmConstants.kGripperOpen, m_arm.getGripper(), DELTA);
  }

  @Test
  void positionsAreClampedToTheServoRange() {
    m_arm.setLift(1.5);
    assertEquals(1.0, m_arm.getLift(), DELTA);

    m_arm.setLift(-0.5);
    assertEquals(0.0, m_arm.getLift(), DELTA);
  }

  @Test
  void nudgeMovesFromTheCurrentPosition() {
    m_arm.setTilt(0.5);
    m_arm.nudgeTilt(0.25);

    assertEquals(0.75, m_arm.getTilt(), DELTA);
  }
}
