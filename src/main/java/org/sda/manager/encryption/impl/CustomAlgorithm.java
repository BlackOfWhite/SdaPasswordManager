package org.sda.manager.encryption.impl;

import org.sda.manager.encryption.EncryptorDecryptor;

/**
 * If passPhrase is shorted then encrypted text, then start passphrase over.
 */
public class CustomAlgorithm implements EncryptorDecryptor {

  private char[] passPhrase;

  public CustomAlgorithm(char[] passPhrase) {
    this.passPhrase = passPhrase;
  }

  public char[] encrypt(char[] text) {
    char[] output = new char[text.length];
    for (int i = 0; i < text.length; i++) {
      char c = text[i];
      char p = getPassPhraseCharAtIndex(i);
      if (i % 2 == 0) {
        output[i] = (char) (c + p);
      } else {
        output[i] = (char) (c - p);
      }
    }
    return output;
  }

  public char[] decrypt(char[] text) {
    char[] output = new char[text.length];
    for (int i = 0; i < text.length; i++) {
      char c = text[i];
      char p = getPassPhraseCharAtIndex(i);
      if (i % 2 == 0) {
        output[i] = (char) (c - p);
      } else {
        output[i] = (char) (c + p);
      }
    }
    return output;
  }

  /**
   * input abcdefg
   * pass xyz
   */
  private char getPassPhraseCharAtIndex(int i) {
    int index = i % passPhrase.length;
    return passPhrase[index];
  }
}
