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
  CityRepository cityRepository;
  @Autowired
  CountryRepository countryRepository;
  @Autowired
  WeatherService weatherService;

  public CityService(CityRepository cityRepository, CountryRepository countryRepository,
      WeatherService weatherService) {
    this.cityRepository = cityRepository;
    this.countryRepository = countryRepository;
    this.weatherService = weatherService;

  }

  public CityInfo getCityInfo(String cityName) {

    // look up city info from database. Might be multiple cities with same name.
    List<City> cities = cityRepository.findByName(cityName);

    // If this city is not in the database, return null
    if (cities.size() == 0) {
      return null;
    }

    // Get the first instance of this city returned from the database
    City city = cities.get(0);
    Country country = city.getCountry();
    TempAndTime tempTime = weatherService.getTempAndTime(cityName);

    CityInfo info = new CityInfo(city, country, tempTime);

    return info;
  }
}

