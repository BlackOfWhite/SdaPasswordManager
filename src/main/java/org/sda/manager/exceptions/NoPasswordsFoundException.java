package org.sda.manager.exceptions;

public class NoPasswordsFoundException extends Exception {

  public NoPasswordsFoundException() {
    super();
    System.out.println("User not found.");
  }

}
