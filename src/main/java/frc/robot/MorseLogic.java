package frc.robot;

import frc.robot.Constants.SignalLightConstants;
import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Handles the logic for printing morse code
 *
 * <p>
 * This class has no hardware in it. It is pure logic: numbers and booleans in,
 * a boolean out.
 * That is what makes it easy to test.
 */
public final class MorseLogic {
  public MorseLogic() {
    DataLogManager.log("Disabled. Sending emergency message!");
  }

private int m_unitsPassed = 0;
private int m_lastUnit = -1; // -1 so the very first call counts as a new unit


  /** Each letter maps to its Morse code: "." is a dot and "-" is a dash. */
  private static final Map<Character, String[]> LETTERS = new HashMap<>();

  static {
    LETTERS.put('A', new String[] { ".", "-" });
    LETTERS.put('B', new String[] { "-", ".", ".", "." });
    LETTERS.put('C', new String[] { "-", ".", "-", "." });
    LETTERS.put('D', new String[] { "-", ".", "." });
    LETTERS.put('E', new String[] { "." });
    LETTERS.put('F', new String[] { ".", ".", "-", "." });
    LETTERS.put('G', new String[] { "-", "-", "." });
    LETTERS.put('H', new String[] { ".", ".", ".", "." });
    LETTERS.put('I', new String[] { ".", "." });
    LETTERS.put('J', new String[] { ".", "-", "-", "-" });
    LETTERS.put('K', new String[] { "-", ".", "-" });
    LETTERS.put('L', new String[] { ".", "-", ".", "." });
    LETTERS.put('M', new String[] { "-", "-" });
    LETTERS.put('N', new String[] { "-", "." });
    LETTERS.put('O', new String[] { "-", "-", "-" });
    LETTERS.put('P', new String[] { ".", "-", "-", "." });
    LETTERS.put('Q', new String[] { "-", "-", ".", "-" });
    LETTERS.put('R', new String[] { ".", "-", "." });
    LETTERS.put('S', new String[] { ".", ".", "." });
    LETTERS.put('T', new String[] { "-" });
    LETTERS.put('U', new String[] { ".", ".", "-" });
    LETTERS.put('V', new String[] { ".", ".", ".", "-" });
    LETTERS.put('W', new String[] { ".", "-", "-" });
    LETTERS.put('X', new String[] { "-", ".", ".", "-" });
    LETTERS.put('Y', new String[] { "-", ".", "-", "-" });
    LETTERS.put('Z', new String[] { "-", "-", ".", "." });
  }

  private void print(String message){
      DataLogManager.log(message);
  }

  public void sendMessage(int time, String message) {
    m_unitsPassed = time / 40;
    // Set the boolean before we set the value
    boolean newUnit = m_unitsPassed != m_lastUnit;

    // Set the value so that they are kept in sync but not until we hit the next value
    m_lastUnit = m_unitsPassed;

    // every .2 seconds print something
    int lettersToShow = m_unitsPassed % (message.length() + 1);

    String shown = "";
    for (int i = 0; i < lettersToShow; i++) {
      shown += message.charAt(i) + " ";
    }
    SmartDashboard.putString("Emergency Message", shown);

    // Step 0 of each cycle shows no letters: that is the pause between words.
    if (lettersToShow == 0) {
      if (newUnit) {
        print("Pause between words");
      }
      return;
    }

    // The letter being sent is the last one shown on the dashboard.
    var currentLetter = message.charAt(lettersToShow - 1);
    var morseLetter = LETTERS.get(currentLetter);

    if (newUnit){
      print("Letter we are currently printing" + currentLetter);

      var printLetter = "";
      for (String letter : morseLetter) {
        printLetter += letter + " ";
      }
      print("Morse code we are currently printing" + printLetter);
    }


  }
}