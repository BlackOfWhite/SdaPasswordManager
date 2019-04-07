package org.sda.manager.converters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;

public class Converters {

  private static final Logger logger = Logger.getLogger(Converters.class);

  public static List<Integer> decimalToBinary(int decimal) {
    int current = decimal;
    List<Integer> list = new ArrayList<>();
    while (current > 0) {
      list.add(decimal % 2);
      decimal /= 2;
      current = decimal;
    }
    Collections.reverse(list);
    return list;
  }

  public static String textToHex(String text) {
    StringBuilder hex = new StringBuilder();
    for (int x = 0; x < text.length(); x++) {
      hex.append(decimalToHex((int) text.charAt(x)));
    }
    logger.debug("Converted: " + text + " to hex: " + hex.toString());
    return hex.toString();
  }

  public static String decimalToHex(int decimal) {
    StringBuilder hex = new StringBuilder();
    while (decimal > 0) {
      hex.append(getHexModulo(decimal));
      decimal /= 16;
    }
    return hex.reverse().toString();
  }

  private static char getHexModulo(int decimal) {
    int modulo = decimal % 16;
    if (modulo <= 9) {
      return (char) (modulo + 48);
    } else {
      return (char) (modulo + 55);
    }
  }
}
