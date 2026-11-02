package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.romi.OnBoardIO;
import edu.wpi.first.wpilibj.romi.OnBoardIO.ChannelMode;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.MorseLogic;
import frc.robot.RslLogic;
// import frc.robot.MorseLogic;

/**
 * The LEDs and buttons on the Romi's control board. The yellow LED is our robot signal light.
 *
 * <p>The board has three LEDs (green, yellow, red) and three buttons (A, B, C). Button A and the
 * yellow LED are always available. The other two pins are shared: each is either a button or an
 * LED. Here DIO 1 is the green LED and DIO 2 is button C.
 */
public class SignalLight extends SubsystemBase {
  private final OnBoardIO m_onboardIO = new OnBoardIO(ChannelMode.OUTPUT, ChannelMode.OUTPUT);
  private final MorseLogic m_morseLogic = new MorseLogic("SOS");


  private boolean m_yellowOn = false;
  private boolean m_redOn = false;
  // Counts periodic() calls while disabled. 50 loops is one second.
  private int m_loopCountDisabled = 0;

  /** Runs 50 times per second in every mode, called by the command scheduler. */
  @Override
  public void periodic() {
    double now = Timer.getFPGATimestamp();

    boolean enabled = DriverStation.isEnabled();
    if (!enabled) {
      m_loopCountDisabled++;
    }

    m_yellowOn = RslLogic.shouldBeOn(enabled, now);
    m_redOn = m_morseLogic.shouldBeOn(enabled, m_loopCountDisabled);
    m_onboardIO.setYellowLed(m_yellowOn);
    m_onboardIO.setRedLed(m_redOn);
  }

  /** Whether the yellow LED is on right now. */
  public boolean isYellowOn() {
    return m_yellowOn;
  }

  /** Turns the green LED on or off. Session 4 binds this to a controller button. */
  public void setGreen(boolean on) {
    m_onboardIO.setGreenLed(on);
  }

  public void setRed(boolean on) {
    m_onboardIO.setRedLed(on);
  }

  public boolean isRedOn(){
    return m_redOn;
  }

  /** Whether button A on the Romi is held down. */
  public boolean isButtonAPressed() {
    return m_onboardIO.getButtonAPressed();
  }
}
