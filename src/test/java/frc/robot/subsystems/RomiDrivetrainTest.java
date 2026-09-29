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
  static final double CIRCUMFERENCE_INCH = Math.PI * DriveConstants.kWheelDiameterInch;

  RomiDrivetrain m_drivetrain;
  EncoderSim m_leftEncoderSim;
  EncoderSim m_rightEncoderSim;

  /** Runs before every test: start the simulated hardware and build a fresh drivetrain. */
  @BeforeEach
  void setUp() {
    assertTrue(HAL.initialize(500, 0));
    m_drivetrain = new RomiDrivetrain();
    // The simulated encoders must be looked up after the drivetrain creates the real ones.
    m_leftEncoderSim = EncoderSim.createForChannel(DriveConstants.kLeftEncoderChannelA);
    m_rightEncoderSim = EncoderSim.createForChannel(DriveConstants.kRightEncoderChannelA);
  }

  /** Runs after every test: free the hardware so the next test can create it again. */
  @AfterEach
  void tearDown() {
    m_drivetrain.close();
  }

  @Test
  void oneWheelTurnIsOneCircumference() {
    m_leftEncoderSim.setCount((int) DriveConstants.kCountsPerRevolution);

    assertEquals(CIRCUMFERENCE_INCH, m_drivetrain.getLeftDistanceInch(), DELTA);
  }

  @Test
  void resetEncodersGoesBackToZero() {
    m_leftEncoderSim.setCount(720);

    m_drivetrain.resetEncoders();

    assertEquals(0.0, m_drivetrain.getLeftDistanceInch(), DELTA);
  }

  @Test
  void halfATurnOnTheRightWheelIsHalfACircumference() {
    m_rightEncoderSim.setCount(720);

    assertEquals(CIRCUMFERENCE_INCH / 2.0, m_drivetrain.getRightDistanceInch(), DELTA);
  }

  @Test
  void averageDistanceIsTheMeanOfBothWheels() {
    m_leftEncoderSim.setCount(1440);
    m_rightEncoderSim.setCount(0);

    assertEquals(CIRCUMFERENCE_INCH / 2.0, m_drivetrain.getAverageDistanceInch(), DELTA);
  }
}
