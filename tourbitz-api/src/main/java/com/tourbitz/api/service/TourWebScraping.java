package com.tourbitz.api.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;
import com.tourbitz.api.domain.TourPlanInformation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TourWebScraping {

  private TourPlanService tourPlanService;

  private Gson gson;

  /**
   * Constructor used for inject dependencies.
   * 
   * @param tourPlanService {@link TourPlanService} to be injected.
   */
  public TourWebScraping(TourPlanService tourPlanService) {
    super();
    this.tourPlanService = tourPlanService;
    this.gson = new Gson();
  }

  public void doScraping(String scriptName, String searchText) {

    try {

      Process p = Runtime.getRuntime().exec(String.format("python3 /home/cvarela/dev/tourbitz/%s %s", scriptName, searchText));
      BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));

      String tourPlan;
      List<TourPlanInformation> tours = new ArrayList<>();
      while ((tourPlan = in.readLine()) != null) {

        try {
          TourPlanInformation planInformation =
              this.gson.fromJson(tourPlan, TourPlanInformation.class);
          tours.add(planInformation);
        } catch (Exception e) {
          // TODO: handle exception
        }
      }
      this.tourPlanService.addTourPlan(searchText, tours);
    } catch (Exception e) {
      log.error("There was an error executing the script.", e);
    }
  }
}
