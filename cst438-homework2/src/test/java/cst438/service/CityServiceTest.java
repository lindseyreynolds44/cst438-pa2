package cst438.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import cst438.domain.City;
import cst438.domain.CityInfo;
import cst438.domain.CityRepository;
import cst438.domain.Country;
import cst438.domain.CountryRepository;
import cst438.domain.TempAndTime;


@WebMvcTest(CityService.class)
public class CityServiceTest {

  @MockBean
  private WeatherService weatherService;

  @MockBean
  private CityRepository cityRepository;

  @MockBean
  private CountryRepository countryRepository;

  @MockBean
  private RabbitTemplate rabbitTemplate;
  @MockBean
  private FanoutExchange fanout;

  private CityService cs;


  @SuppressWarnings("deprecation")
  @BeforeEach
  public void setUpBefore() {
    MockitoAnnotations.initMocks(this);
  }


  /**
   * Test what happens in a normal scenario where only one city should be returned
   */
  @Test
  public void test_oneCity() {

    // Set up information in order to create MOCKS
    Country country = new Country("USA", "United States");
    City city = new City(3793, "New York", "New York", 8008278, country);
    List<City> cities = new ArrayList<City>();
    cities.add(city);
    TempAndTime tempTime = new TempAndTime(288.25, 1620587692, -14400);


    // We will test this CityService object
    cs = new CityService(cityRepository, countryRepository, weatherService, rabbitTemplate, fanout);

    // Create MOCKs in order to test the getCityInfo method
    given(weatherService.getTempAndTime("New York")).willReturn(tempTime);
    given(cityRepository.findByName("New York")).willReturn(cities);
    given(countryRepository.findByCode("USA")).willReturn(country);

    // Test the getCityInfo method in CityService class
    CityInfo actualCityInfo = cs.getCityInfo("New York");

    // Create Expected results
    CityInfo expectedCityInfo = new CityInfo(city, country, tempTime);

    // Check that expected matches actual
    assertEquals(actualCityInfo, expectedCityInfo);

  }

  /**
   * Test what happens when multiple cities are returned
   */
  @Test
  public void test_manyCities() {

    // Set up information in order to create MOCKS
    Country country = new Country("CHL", "Chile");
    City city = new City(568, "Los Angeles", "Bíobío", 158215, country);
    List<City> cities = new ArrayList<City>();
    cities.add(city);
    TempAndTime tempTime = new TempAndTime(293.68, 1620592785, -25200);

    // We will test this CityService object
    cs = new CityService(cityRepository, countryRepository, weatherService, rabbitTemplate, fanout);

    // Create MOCKs in order to test the getCityInfo method
    given(weatherService.getTempAndTime("Los Angeles")).willReturn(tempTime);
    given(cityRepository.findByName("Los Angeles")).willReturn(cities);
    given(countryRepository.findByCode("CHL")).willReturn(country);

    // Test the getCityInfo method in CityService class
    CityInfo actualCityInfo = cs.getCityInfo("Los Angeles");

    // Create Expected results
    CityInfo expectedCityInfo = new CityInfo(city, country, tempTime);

    // Check that expected matches actual
    assertEquals(actualCityInfo, expectedCityInfo);

  }

  /**
   * Test what happens when the city does not exist
   */
  @Test
  public void test_cityDoesNotExist() {

    // Set up information in order to create MOCKS
    List<City> cities = new ArrayList<City>();

    // We will test this CityService object
    cs = new CityService(cityRepository, countryRepository, weatherService, rabbitTemplate, fanout);

    // Create MOCKs in order to test the getCityInfo method
    given(cityRepository.findByName("fail")).willReturn(cities);
    // We do not need MOCKs for calls to countryRepository or weatherRepository
    // because those lines of code will never be called in this scenario

    // Test the getCityInfo method in CityService class
    CityInfo actualCityInfo = cs.getCityInfo("fail");

    // Check that the actual result came back as null
    assertThat(actualCityInfo).isEqualTo(null);

  }

}
