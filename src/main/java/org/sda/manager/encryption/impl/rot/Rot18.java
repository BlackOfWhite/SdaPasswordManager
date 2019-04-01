package org.sda.manager.encryption.impl.rot;

public class Rot18 extends AbstractROT {

  private Rot13 rot13 = new Rot13();

  @Override
  public char rotateChar(char c) {
    if (Character.isLetter(c)) {
      return rot13.rotateChar(c);
    } else if (c >= '0' && c <= '4') {
      c += 5;
    } else if (c >= '5' && c <= '9') {
      c -= 5;
    }
    return c;
  }
}
