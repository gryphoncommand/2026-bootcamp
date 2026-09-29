package frc.robot.subsystems;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.simulation.EncoderSim;
import frc.robot.Constants.DriveConstants;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Checks the drivetrain's encoder math using simulated hardware. No Romi needed: WPILib pretends
 * the encoders exist and lets the test set their counts.
 */
class RomiDrivetrainTest {
  static final double DELTA = 1e-6;

  RomiDrivetrain m_drivetrain;
  EncoderSim m_leftEncoderSim;

  /** Runs before every test: start the simulated hardware and build a fresh drivetrain. */
  @BeforeEach
  void setUp() {
    assertTrue(HAL.initialize(500, 0));
    m_drivetrain = new RomiDrivetrain();
    // The simulated encoder must be looked up after the drivetrain creates the real one.
    m_leftEncoderSim = EncoderSim.createForChannel(DriveConstants.kLeftEncoderChannelA);
  }

  /** Runs after every test: free the hardware so the next test can create it again. */
  @AfterEach
  void tearDown() {
    m_drivetrain.close();
  }

  @Test
  void oneWheelTurnIsOneCircumference() {
    m_leftEncoderSim.setCount((int) DriveConstants.kCountsPerRevolution);

    double circumferenceInch = Math.PI * DriveConstants.kWheelDiameterInch;
    assertEquals(circumferenceInch, m_drivetrain.getLeftDistanceInch(), DELTA);
  }

  @Test
  void resetEncodersGoesBackToZero() {
    m_leftEncoderSim.setCount(720);

    m_drivetrain.resetEncoders();

    assertEquals(0.0, m_drivetrain.getLeftDistanceInch(), DELTA);
  }
}
