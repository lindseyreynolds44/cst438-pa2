package cst438.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import cst438.domain.CityInfo;
import cst438.service.CityService;

@Controller
public class CityController {

  @Autowired
  private CityService cityService;

  /**
   * Display the city that the user searched for
   * 
   * @param cityName
   * @param model
   * @return html template to display the city info
   */
  @GetMapping("/cities/{city}")
  public String getCityInfo(@PathVariable("city") String cityName, Model model) {

    CityInfo cityInfo = cityService.getCityInfo(cityName);

    if (cityInfo == null) {
      return "not_found";
    }
    model.addAttribute(cityInfo);

    return "showcity";
  }

  @PostMapping("/cities/reservation")
  public String createReservation(@RequestParam("city") String cityName,
      @RequestParam("level") String level, @RequestParam("email") String email, Model model) {
    model.addAttribute("city", cityName);
    model.addAttribute("level", level);
    model.addAttribute("email", email);

    System.out.println(cityName + " " + level + " " + email);

    cityService.requestReservation(cityName, level, email);
    return "request_reservation";
  }
}
