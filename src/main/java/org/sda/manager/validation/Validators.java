package org.sda.manager.validation;

import java.util.Arrays;

public class Validators {

  public static boolean validatePassword(String password) {
    if (!isLengthWithinRange(password, 3, 20)) {
      return false;
    }
    if (isNumberOfUpperCasesCorrect(password, 2)) {
      return true;
    }
    return false;
  }

  public static boolean compareArrays(char[] arr1, char[] arr2) {
    return Arrays.equals(arr1, arr2);
  }

  public static boolean validateEmail(String email) {
    if (!isLengthWithinRange(email, 7, 20)) {
      return false;
    }
    char[] legalCharacters = new char[]{'@', '.', '-'};
    if (numberOfOccurrences(email, '@') != 1) {
      return false;
    }
    if (!containsValidCharacterSet(email, legalCharacters)) {
      return false;
    }
    // znaki specjalnie nie moga byyc obok siebie
    return true;
  }

  private static int numberOfOccurrences(String s, char c) {
    int count = 0;
    for (int x = 0; x < s.length(); x++) {
      if (s.charAt(x) == c) {
        count++;
      }
    }
    return count;
  }

  public static boolean isNumberOfUpperCasesCorrect(String password, int count) {
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

  /**
   * Indexing starts with 1.
   */
  public static boolean isLengthWithinRange(String s, int startPos, int endPos) {
    if (s.length() >= startPos && s.length() <= endPos) {
      return true;
    }
    return false;
  }

  /**
   * Check if given string contains only letter and digits.
   *
   * @param legalCharacterSet Additional, extended set of allowed characters.
   */
  public static boolean containsValidCharacterSet(String s, char[] legalCharacterSet) {
    for (int x = 0; x < s.length(); x++) {
      char c = s.charAt(x);
      if (Character.isLetter(c) || Character.isDigit(c)) {
        continue;
      } else {
        // check additional legal characters
        boolean contains = false;
        for (int i = 0; i < legalCharacterSet.length; i++) {
          if (c == legalCharacterSet[i]) {
            contains = true;
            break;
          }
        }
        // not found
        if (contains == false) {
          return false;
        }
      }
    }
    return true;
  }


}
