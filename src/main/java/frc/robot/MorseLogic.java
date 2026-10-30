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

private static int m_unitsPassed = 0;

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

  public void sendMessage(int time, String message) {
    m_unitsPassed = time / 40;

    // every .2 seconds print something
    int lettersToShow = m_unitsPassed % (message.length() + 1);

    String shown = "";
    for (int i = 0; i < lettersToShow; i++) {
      shown += message.charAt(i) + " ";
    }
    SmartDashboard.putString("Emergency Message", shown);

    if (time % 40 == 0){
      DataLogManager.log("Letter we are currently printing" + message.charAt(lettersToShow % message.length()));
    }
    var currentLetter = message.charAt(lettersToShow % message.length());
    var morseLetter = LETTERS.get(currentLetter);
    
    if (time % 40 == 0){
      DataLogManager.log("Morse code we are currently printing" + morseLetter.toString());
    }


  }
}