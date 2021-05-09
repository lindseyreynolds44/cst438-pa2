package cst438.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cst438.domain.City;
import cst438.domain.CityInfo;
import cst438.domain.CityRepository;
import cst438.domain.Country;
import cst438.domain.CountryRepository;
import cst438.domain.TempAndTime;

@Service
public class CityService {

  @Autowired
  private CityRepository cityRepository;
  @Autowired
  private CountryRepository countryRepository;
  @Autowired
  private WeatherService weatherService;

  public CityInfo getCityInfo(String cityName) {

    // look up city info from database. Might be multiple cities with same name.
    List<City> cities = cityRepository.findByName(cityName);

    if (cities.size() == 0) {
      return null;
    }

    City city = cities.get(0);
    Country country = countryRepository.findByCode(city.getCountryCode());
    TempAndTime tempTime = weatherService.getTempAndTime(cityName);

    CityInfo info = new CityInfo(city, country, tempTime);

    return info;
  }
}

