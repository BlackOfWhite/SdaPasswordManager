package tests.converters;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.sda.manager.converters.Converters;

public class ConvertersTests {

  @Test
  public void decimalToBinaryTest() {
    List<Integer> expected = Arrays.asList(1, 1, 1, 1, 1, 0, 0);
    assertEquals("Lists are expected to be equal.", expected, Converters.decimalToBinary(124));
  }

  @Test
  public void decimalToHexTest() {
    String expected = "1AF";
    assertEquals("Values are expected to be equal.", expected, Converters.decimalToHex(431));
  }

  @Test
  public void textToHexTest() {
    String expected = "52656420666F782E";
    String actual = "Red fox.";
    assertEquals("Values are expected to be equal.",
        expected, Converters.textToHex(actual));

  }

}
