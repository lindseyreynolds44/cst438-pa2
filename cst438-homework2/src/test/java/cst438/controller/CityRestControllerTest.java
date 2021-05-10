package cst438.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import cst438.domain.City;
import cst438.domain.CityInfo;
import cst438.domain.Country;
import cst438.domain.TempAndTime;
import cst438.service.CityService;

@WebMvcTest(CityRestController.class)
public class CityRestControllerTest {

  @MockBean
  private CityService cityService;

  @Autowired
  private MockMvc mvc;

  private JacksonTester<CityInfo> jsonCityAttempt;

  @SuppressWarnings("deprecation")
  @BeforeEach
  public void setUpEach() {
    MockitoAnnotations.initMocks(this);
    JacksonTester.initFields(this, new ObjectMapper());
  }

  /**
   * Test what happens in a normal scenario where only one city should be returned
   */
  @Test
  public void test_oneCity() throws Exception {

    // Set up information in order to create MOCKS
    Country country = new Country("USA", "United States");
    City city = new City(3793, "New York", "New York", 8008278, country);
    TempAndTime tempTime = new TempAndTime(288.25, 1620587692, -14400);
    CityInfo cityInfo = new CityInfo(city, country, tempTime);

    given(cityService.getCityInfo("New York")).willReturn(cityInfo);

    // perform the test by making simulated HTTP get using URL of "/api/cities/New York"
    MockHttpServletResponse response =
        mvc.perform(get("/api/cities/New York")).andReturn().getResponse();

    // verify that the status code is as expected
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    // convert returned data from JSON string format to CityInfo object
    CityInfo actualResult = jsonCityAttempt.parseObject(response.getContentAsString());

    // Create the expected CityInfo object
    CityInfo expectedResult = new CityInfo(city, country, tempTime);

    // compare actual return data with expected data
    assertThat(actualResult).isEqualTo(expectedResult);
    assertThat(actualResult.getTime()).isEqualTo("3:14 PM");
    assertThat(actualResult.getTemp()).isEqualTo("59.18 F");
  }


  /**
   * Test what happens when multiple cities are returned
   */
  @Test
  public void test_manyCities() throws Exception {
    // Set up information in order to create MOCKS
    Country country = new Country("CHL", "Chile");
    City city = new City(568, "Los Angeles", "Biobio", 158215, country);
    TempAndTime tempTime = new TempAndTime(293.68, 1620592785, -25200);
    CityInfo cityInfo = new CityInfo(city, country, tempTime);


    given(cityService.getCityInfo("Los Angeles")).willReturn(cityInfo);

    // perform the test by making simulated HTTP get using URL of "/api/cities/Los Angeles"
    MockHttpServletResponse response =
        mvc.perform(get("/api/cities/Los Angeles")).andReturn().getResponse();

    // verify that the status code is as expected
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    // convert returned data from JSON string format to CityInfo object
    CityInfo actualResult = jsonCityAttempt.parseObject(response.getContentAsString());

    CityInfo expectedResult = new CityInfo(city, country, tempTime);

    // compare actual return data with expected data
    assertThat(actualResult).isEqualTo(expectedResult);
    assertThat(actualResult.getTime()).isEqualTo("1:39 PM");
    assertThat(actualResult.getTemp()).isEqualTo("68.95 F");

  }

  /**
   * Test what happens when the city does not exist
   */
  @Test
  public void test_cityDoesNotExist() throws Exception {


    given(cityService.getCityInfo("fail")).willReturn(null);

    // perform the test by making simulated HTTP get using URL of "/api/cities/Los Angeles"
    MockHttpServletResponse response =
        mvc.perform(get("/api/cities/fail")).andReturn().getResponse();

    // verify that the status code is as expected
    assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
  }

}
