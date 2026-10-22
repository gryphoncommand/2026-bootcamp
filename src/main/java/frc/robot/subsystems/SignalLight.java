package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.romi.OnBoardIO;
import edu.wpi.first.wpilibj.romi.OnBoardIO.ChannelMode;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.MorseLogic;
import frc.robot.RslLogic;

/**
 * The LEDs and buttons on the Romi's control board. The yellow LED is our robot signal light. The
 * red LED flashes SOS while the robot is disabled.
 *
 * <p>The board has three LEDs (green, yellow, red) and three buttons (A, B, C). Button A and the
 * yellow LED are always available. The other two pins are shared: each is either a button or an
 * LED. Here DIO 1 is the green LED and DIO 2 is the red LED, so button C is not available.
 */
public class SignalLight extends SubsystemBase {
  private final OnBoardIO m_onboardIO = new OnBoardIO(ChannelMode.OUTPUT, ChannelMode.OUTPUT);

  private boolean m_yellowOn = false;

  /** Runs 50 times per second in every mode, called by the command scheduler. */
  @Override
  public void periodic() {
    boolean enabled = DriverStation.isEnabled();
    double now = Timer.getFPGATimestamp();
    m_yellowOn = RslLogic.shouldBeOn(enabled, now);
    m_onboardIO.setYellowLed(m_yellowOn);

    // Red LED: SOS while disabled, off while enabled.
    m_onboardIO.setRedLed(!enabled && MorseLogic.sosShouldBeOn(now));
  }

  /** Whether the yellow LED is on right now. */
  public boolean isYellowOn() {
    return m_yellowOn;
  }

  /** Turns the green LED on or off. Session 4 binds this to a controller button. */
  public void setGreen(boolean on) {
    m_onboardIO.setGreenLed(on);
  }

  /** Whether button A on the Romi is held down. */
  public boolean isButtonAPressed() {
    return m_onboardIO.getButtonAPressed();
  }
}
