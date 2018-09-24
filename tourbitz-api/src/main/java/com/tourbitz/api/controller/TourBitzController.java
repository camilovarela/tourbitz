package com.tourbitz.api.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tourbitz.api.domain.TourPlanInformation;
import com.tourbitz.api.service.TourPlanService;
import com.tourbitz.api.service.TourWebScraping;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api
public class TourBitzController {

  private TourWebScraping tourWebScraping;

  private TourPlanService tourPlanService;

  public TourBitzController(TourWebScraping tourWebScraping, TourPlanService tourPlanService) {
    super();
    this.tourWebScraping = tourWebScraping;
    this.tourPlanService = tourPlanService;
  }

  @GetMapping(value = "/tours/{search}")
  @ApiOperation(value = "View a list of all plans")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully retrieved list"),
      @ApiResponse(code = 401, message = "You are not authorized to view the resource")})
  public ResponseEntity<List<TourPlanInformation>> retrieveTourPlans(
      @PathVariable("search") String searchText) {
    return ResponseEntity.ok(this.tourPlanService.retreiveTourPlans(searchText));
  }

  @Async
  @PostMapping(value = "/{aggregator}/tours/{search}")
  public void doScraping(@PathVariable("aggregator") String aggregator,
      @PathVariable("search") String searchText) {
    if ("getyourguide".equals(aggregator)) {
      this.tourWebScraping.doScraping("webscraping_tourbitz.py", searchText);
    }
  }
}
