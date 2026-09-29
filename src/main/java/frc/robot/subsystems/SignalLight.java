package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.romi.OnBoardIO;
import edu.wpi.first.wpilibj.romi.OnBoardIO.ChannelMode;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RslLogic;

/**
 * The LEDs and buttons on the Romi's control board. The yellow LED is our robot signal light.
 *
 * <p>The board has three LEDs (green, yellow, red) and three buttons (A, B, C). Button A and the
 * yellow LED are always available. The other two pins are shared: each is either a button or an
 * LED. Here DIO 1 is the green LED and DIO 2 is button C.
 */
public class SignalLight extends SubsystemBase {
  private final OnBoardIO m_onboardIO = new OnBoardIO(ChannelMode.OUTPUT, ChannelMode.INPUT);

  private boolean m_yellowOn = false;

  /** Runs 50 times per second in every mode, called by the command scheduler. */
  @Override
  public void periodic() {
    boolean enabled = DriverStation.isEnabled();
    double now = Timer.getFPGATimestamp();
    m_yellowOn = RslLogic.shouldBeOn(enabled, now);
    m_onboardIO.setYellowLed(m_yellowOn);
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
