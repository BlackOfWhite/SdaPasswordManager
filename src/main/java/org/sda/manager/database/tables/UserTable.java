package org.sda.manager.database.tables;

public class UserTable {

  private String userName;
  private String hash;
  private String country;
  private String email;

  public UserTable(String userName, String email, String country, String hash) {
    this.userName = userName;
    this.hash = hash;
    this.country = country;
    this.email = email;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getHash() {
    return hash;
  }

  public void setHash(String hash) {
    this.hash = hash;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String toString() {
    return "UserTable{" +
        "userName='" + userName + '\'' +
        ", hash='" + hash + '\'' +
        ", country='" + country + '\'' +
        ", email='" + email + '\'' +
        '}';
  }
}
