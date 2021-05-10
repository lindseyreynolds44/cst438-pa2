package cst438.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import cst438.domain.CityInfo;
import cst438.service.CityService;

@RestController
public class CityRestController {

  @Autowired
  private CityService cityService;

  /**
   * Display the JSON data for the city the user searched for or a 404 not found error if the city
   * is not found
   * 
   * @param cityName
   * @return a CityInfo object or 404 error
   */
  @GetMapping("/api/cities/{city}")
  public CityInfo getWeather(@PathVariable("city") String cityName) {
    CityInfo cityInfo = cityService.getCityInfo(cityName);

    if (cityInfo == null) {
      // city not found
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "The city " + cityName + " was not found.");
    }

    return cityInfo;
  }
}
