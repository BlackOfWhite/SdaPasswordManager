package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.sda.manager.encryption.EncryptorDecryptor;
import org.sda.manager.encryption.impl.CustomAlgorithm;
import org.sda.manager.encryption.impl.rot.Rot13;
import org.sda.manager.encryption.impl.rot.Rot18;

public class EncryptionTests {

  @Test
  public void customAlgorithmTest() {
    char[] text = "Red fox jumpe a lot".toCharArray();
    EncryptorDecryptor encryptorDecryptor = new CustomAlgorithm("passphrase".toCharArray());
    char[] encryptedText = encryptorDecryptor.encrypt(text);
    assertEquals("Encrypted text is not correct",
        new String("Â×ﾭÖê﾿ÝÝØﾭÑﾸÞç"), new String(encryptedText));
  }

  @Test
  public void customAlgorithmFlowTest() {
    char[] text = "Red fox jumpe a lot".toCharArray();
    EncryptorDecryptor encryptorDecryptor = new CustomAlgorithm("passphrase".toCharArray());
    char[] encryptedText = encryptorDecryptor.encrypt(text);
    char[] decryptedText = encryptorDecryptor.decrypt(encryptedText);
    assertEquals("Encrypted text is not correct",
        new String("Red fox jumpe a lot"), new String(decryptedText));
  }

  @Test
  public void rot13MirrorTest() {
    char[] text = "Red fox jumpes a lot?><M345".toCharArray();
    EncryptorDecryptor encoder = new Rot13();
    char[] encrypted = encoder.encrypt(text);
    char[] decrypted = encoder.encrypt(encrypted);
    assertEquals("Text doesn't match expected result.", "Red fox jumpes a lot?><M345", new String(decrypted));
  }

  @Test
  public void rot13EncryptionTest() {
    EncryptorDecryptor encryptorDecryptor = new Rot13();
    final String text = "test123TESTr0T";
    char[] encryptedText = encryptorDecryptor.encrypt(text.toCharArray());
    assertEquals("Encrypted text is not correct", "grfg123GRFGe0G", new String(encryptedText));
  }

  @Test
  public void rot13DecryptionTest() {
    EncryptorDecryptor encryptorDecryptor = new Rot13();
    final String text = "2cvaxSbkWhzc GehSrapr";
    char[] decryptedText = encryptorDecryptor.decrypt(text.toCharArray());
    assertEquals("Decrypted text is not correct", "2pinkFoxJump TruFence", new String(decryptedText));
  }

  @Test
  public void rot13InversionTest() {
    EncryptorDecryptor encryptorDecryptor = new Rot13();
    final String originalText = ".?O123_ loop^A T";
    final String expectedEncrypted = ".?B123_ ybbc^N G";
    char[] encryptedText = encryptorDecryptor.encrypt(originalText.toCharArray());
    assertEquals("Encrypted text is not correct", expectedEncrypted, new String(encryptedText));
    char[] decryptedText = encryptorDecryptor.decrypt(encryptedText);
    assertEquals("Decrypted text is not correct", originalText, new String(decryptedText));
  }

  @Test
  public void rot18EncryptionTest() {
    EncryptorDecryptor encryptorDecryptor = new Rot18();
    final String text = "The quick brown fox jumps over 68 lazy dogs.";
    char[] encryptedText = encryptorDecryptor.encrypt(text.toCharArray());
    assertEquals("Encrypted text is not correct", "Gur dhvpx oebja sbk whzcf bire 13 ynml qbtf.", new String(encryptedText));
  }

  @Test
  public void rot138DecryptionTest() {
  }

  @Test
  public void rot18InversionTest() {
  }
}
