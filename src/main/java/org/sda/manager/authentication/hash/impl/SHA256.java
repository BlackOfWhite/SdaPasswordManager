package org.sda.manager.authentication.hash.impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.sda.manager.authentication.hash.HashFunction;

public class SHA256 implements HashFunction {

  @Override
  public String hash(char[] text) {
    return DigestUtils.sha256Hex(new String(text));
  }
}
