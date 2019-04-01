package org.sda.manager.authentication.model;

public class Address {

  private String country;
  private String city;
  private String street;
  private int flatNumber;

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public int getFlatNumber() {
    return flatNumber;
  }

  public void setFlatNumber(int flatNumber) {
    this.flatNumber = flatNumber;
  }

  @Override
  public String toString() {
    return "Address{" +
        "country='" + country + '\'' +
        ", city='" + city + '\'' +
        ", street='" + street + '\'' +
        ", flatNumber=" + flatNumber +
        '}';
  }
}
