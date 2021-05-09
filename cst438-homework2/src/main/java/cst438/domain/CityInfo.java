package cst438.domain;

public class CityInfo {

  private City city;
  private Country country;
  private TempAndTime tempAndTime;

  public CityInfo() {}

  public CityInfo(City city, Country country, TempAndTime tempAndTime) {
    super();
    this.city = city;
    this.country = country;
    this.tempAndTime = tempAndTime;
  }

  public City getCity() {
    return city;
  }

  public void setCity(City city) {
    this.city = city;
  }

  public Country getCountry() {
    return country;
  }

  public void setCountry(Country country) {
    this.country = country;
  }

  public TempAndTime getTempAndTime() {
    return tempAndTime;
  }

  public void setTempAndTime(TempAndTime tempAndTime) {
    this.tempAndTime = tempAndTime;
  }
}
