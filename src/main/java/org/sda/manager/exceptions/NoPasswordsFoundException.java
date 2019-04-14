package org.sda.manager.exceptions;

public class UserNotFoundException extends Exception {

  public UserNotFoundException() {
    super();
    System.out.println("User not found.");
  }

}
