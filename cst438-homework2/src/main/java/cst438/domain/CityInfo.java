package cst438.domain;

public class CityInfo {

  private City city;
  private Country country;
  private String temp;
  private String time;

  public CityInfo() {}

  public CityInfo(City city, Country country, TempAndTime tempAndTime) {
    super();
    this.city = city;
    this.country = country;
    this.temp = tempAndTime.getTempFahrenheit();
    this.time = tempAndTime.getTimeFormatted();
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

  public String getTemp() {
    return temp;
  }

  public void setTemp(String temp) {
    this.temp = temp;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    CityInfo other = (CityInfo) obj;
    if (city == null) {
      if (other.city != null)
        return false;
    } else if (!city.equals(other.city))
      return false;
    if (country == null) {
      if (other.country != null)
        return false;
    } else if (!country.equals(other.country))
      return false;
    if (temp == null) {
      if (other.temp != null)
        return false;
    } else if (!temp.equals(other.temp))
      return false;
    if (time == null) {
      if (other.time != null)
        return false;
    } else if (!time.equals(other.time))
      return false;
    return true;
  }



}
