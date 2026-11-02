package frc.robot;

import frc.robot.Constants.SignalLightConstants;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

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

  public MorseLogic(String disabledMessage){
    m_lightStatus = generateEncoding(disabledMessage);
    m_unitLabels = generateLabels(disabledMessage);
  }

  private int m_unitsPassed = 0;
  private int m_lastUnit = -1; // -1 so the very first call counts as a new unit
  private List<Integer> m_lightStatus = new ArrayList<Integer>();
  private List<String> m_unitLabels = new ArrayList<String>();

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

  private void print(String message) {
    DataLogManager.log(message);
  }

  public List<Integer> generateEncoding(String message) {
    List<Integer> signals = new ArrayList<Integer>();

    // This will be an individual Letter IE 'S'
    for (char letter : message.toCharArray()) {

      // A single symbol "." or "-"
      var morseLetter = LETTERS.get(letter);

      for (String symbolString : morseLetter) {
        if (symbolString.equals(".")) {
          signals.add(1);
        } else if (symbolString.equals("-")) {
          signals.addAll(List.of(1, 1, 1));
        }
        // Add the gap between each '.' and '-'
        signals.add(0);
      }
      // Add a gap of 0 0 after each symbol. There is already one from the gap between words
        signals.addAll(List.of(0, 0));
    }
    // After each word add 4 0s. Same issue as above both gaps get added
    signals.addAll(List.of(0, 0, 0, 0));
    return signals;
  }

  /**
   * Makes one label for each unit, lined up with generateEncoding. A dash gets its label 3 times
   * because it lasts 3 units.
   */
  public List<String> generateLabels(String message) {
    List<String> labels = new ArrayList<String>();

    for (int i = 0; i < message.length(); i++) {
      char letter = message.charAt(i);
      String[] morseLetter = LETTERS.get(letter);
      // Grows as each symbol is sent: "S .", then "S . .", then "S . . ."
      String sentSoFar = "" + letter;

      for (int j = 0; j < morseLetter.length; j++) {
        sentSoFar += " " + morseLetter[j];
        // A dot is on for 1 unit, a dash for 3
        int unitsOn = morseLetter[j].equals("-") ? 3 : 1;
        for (int k = 0; k < unitsOn; k++) {
          labels.add(sentSoFar);
        }
        // Gap between symbols, but not after the letter's last symbol
        if (j < morseLetter.length - 1) {
          labels.add("symbol gap");
        }
      }
      // Gap between letters, but not after the last letter
      if (i < message.length() - 1) {
        labels.addAll(List.of("letter gap", "letter gap", "letter gap"));
      }
    }
    labels.addAll(List.of("word gap", "word gap", "word gap", "word gap",
        "word gap", "word gap", "word gap"));
    return labels;
  }

  public void sendMessage(int time, String message) {
    m_unitsPassed = time / 40;
    // Set the boolean before we set the value
    boolean newUnit = m_unitsPassed != m_lastUnit;

    // Set the value so that they are kept in sync but not until we hit the next
    // value
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

    if (newUnit) {
      print("Letter we are currently printing" + currentLetter);
      var printLetter = "";
      for (String letter : morseLetter) {
        printLetter += letter + " ";
      }

      print("Morse code we are currently printing" + printLetter);
    }
  }

  /**
   * This handles the logic displaying a message when disabled. 
   * It changes status every .4 seconds 
   * @param enabled true when the robot is enabled
   * @param loopCount how many 20 ms loops have run (50 loops is one second)
   * @return true when the light should be on
   */
  public boolean shouldBeOn(boolean enabled, int loopCount) {
    if (enabled) {
      return false; // solid off while enabled
    }
    // Each unit lasts 40 loops. % wraps back to the start when the message ends.
    int unit = (loopCount / 40) % m_lightStatus.size();
    return m_lightStatus.get(unit) == 1;
  }

  /** What the light is sending right now, like "S . ." or "letter gap". */
  public String currentSymbol(int loopCount) {
    int unit = (loopCount / 40) % m_unitLabels.size();
    return m_unitLabels.get(unit);
  }
}