package tests;

import org.junit.Assert;
import org.junit.Test;
import org.sda.manager.authentication.hash.impl.SHA256;

public class HashTests {

  @Test
  public void SHA256Test() {
    String hash = new SHA256().hash("password".toCharArray());
    String expected = "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8";
    Assert.assertEquals("Expected both hashes to be equal.",
        expected, hash);
  }
}
