package cst438.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import cst438.domain.CityInfo;
import cst438.service.CityService;

@Controller
public class CityController {

  @Autowired
  private CityService cityService;

  @GetMapping("/index")
  public String displayLandingPage(Model model) {
    return "index";
  }

  @GetMapping("/cities/{city}")
  public String getCityInfo(@PathVariable("city") String cityName, Model model) {

    CityInfo cityInfo = cityService.getCityInfo(cityName);

    if (cityInfo == null) {
      return "not_found";
    }

    model.addAttribute(cityInfo);

    return "display_city";
  }
}
