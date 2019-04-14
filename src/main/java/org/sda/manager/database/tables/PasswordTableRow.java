package org.sda.manager.database.tables;

public class PasswordTableRow {

  private String id;
  private String title;
  private String desc;
  private String email;
  private String url;
  private String password;
  private RotType rotType;
  private String userId;

  public PasswordTableRow(String id, String userId, String title, RotType rotType, String password) {
    this.id = id;
    this.userId = userId;
    this.title = title;
    this.rotType = rotType;
    this.password = password;
  }

  public PasswordTableRow desc(String description) {
    setDesc(description);
    return this;
  }

  public PasswordTableRow email(String email) {
    setEmail(email);
    return this;
  }

  public PasswordTableRow url(String url) {
    setUrl(url);
    return this;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public RotType getRotType() {
    return rotType;
  }

  @Override
  public String toString() {
    return "PasswordTableRow{" +
        "id='" + id + '\'' +
        ", title='" + title + '\'' +
        ", desc='" + desc + '\'' +
        ", email='" + email + '\'' +
        ", url='" + url + '\'' +
        ", password='" + password + '\'' +
        ", rotType=" + rotType +
        '}';
  }
}
