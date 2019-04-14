package org.sda.manager.database.tables;

import org.sda.manager.authentication.hash.impl.SHA256;
import org.sda.manager.authentication.model.User;

public class UserTableRow {

  private String id;
  private String userName;
  private String hash;
  private String country;
  private String email;

  public UserTableRow(String id, String userName, String email, String country, String hash) {
    this.id = id;
    this.userName = userName;
    this.hash = hash;
    this.country = country;
    this.email = email;
  }

  public UserTableRow(User user) {
    this.id = "";
    this.userName = user.getName();
    this.hash = new SHA256().hash(user.getPassword());
    this.country = user.getCountry();
    this.email = user.getEmail();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String[] toRow() {
    return new String[]{id, userName, email, country, hash};
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
    return "UserTableRow{" +
        "id='" + id + '\'' +
        ", userName='" + userName + '\'' +
        ", hash='" + hash + '\'' +
        ", country='" + country + '\'' +
        ", email='" + email + '\'' +
        '}';
  }
}
