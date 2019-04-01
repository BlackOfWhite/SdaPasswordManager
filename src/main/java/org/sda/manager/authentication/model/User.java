package org.sda.manager.authentication.model;

import java.util.Arrays;
import java.util.Objects;

public class User {

  private String name;
  private char[] password;
  private Address address;

  public User(String name, char[] password) {
    this.name = name;
    this.password = password;
  }
  public User(String name, String password) {
    this.name = name;
    this.password = password.toCharArray();
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public char[] getPassword() {
    return password;
  }

  @Override
  public String toString() {
    return "User{" +
        "name='" + name + '\'' +
        ", password=" + Arrays.toString(password) +
        ", address=" + address +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return name.equals(user.name) && new String(password).equals(new String(user.password));
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(name, address);
    result = 31 * result + Arrays.hashCode(password);
    return result;
  }
}
