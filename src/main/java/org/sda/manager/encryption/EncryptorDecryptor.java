package org.sda.manager.encryption;

public interface EncryptorDecryptor {

  char[] encrypt(char[] text);

  char[] decrypt(char[] text);

}
