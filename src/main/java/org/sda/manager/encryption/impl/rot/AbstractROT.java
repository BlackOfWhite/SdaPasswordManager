package org.sda.manager.encryption.impl.rot;

import org.sda.manager.encryption.EncryptorDecryptor;

public abstract class AbstractROT implements EncryptorDecryptor {

  public char[] encrypt(char[] text) {
    return init(text);
  }

  public char[] decrypt(char[] text) {
    return init(text);
  }

  private char[] init(char[] text) {
    char[] output = new char[text.length];
    for (int i = 0; i < text.length; i++) {
      output[i] = rotateChar(text[i]);
    }
    return output;
  }

  protected abstract char rotateChar(char c);
}
