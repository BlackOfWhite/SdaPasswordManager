package org.sda.manager.encryption.impl.rot;

public class Rot13 extends AbstractROT {

  @Override
  public char rotateChar(char c) {
    if (c >= 'a' && c <= 'm') {
      c += 13;
    } else if (c >= 'A' && c <= 'M') {
      c += 13;
    } else if (c >= 'n' && c <= 'z') {
      c -= 13;
    } else if (c >= 'N' && c <= 'Z') {
      c -= 13;
    }
    return c;
  }
}
