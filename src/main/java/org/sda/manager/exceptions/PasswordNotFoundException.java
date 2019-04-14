package org.sda.manager.exceptions;

public class PasswordNotFoundException extends Exception {

  public PasswordNotFoundException() {
    super();
    System.out.println("Password not found.");
  }

}
