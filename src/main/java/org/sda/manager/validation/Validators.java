package org.sda.manager.validation;

public class Validators {

  public static boolean validatePassword(String password) {
    if (!isWithinRange(password, 3, 20)) {
      return false;
    }
    if (isUpperCase(password, 2)) {
      return true;
    }
    return false;
  }

  public static boolean isUpperCase(String password, int count) {
    int upperCaseCount = 0;
    for (int a = 0; a < password.length(); a++) {
      char c = password.charAt(a);
      if (Character.isUpperCase(c)) {
        upperCaseCount++;
      }
    }
    if (upperCaseCount >= count) {
      return true;
    }
    return false;
  }

  public static boolean isWithinRange(String s, int startPos, int endPos) {
    if (s.length() >= startPos && s.length() < endPos) {
      return true;
    }
    return false;
  }


}
