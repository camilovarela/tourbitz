package com.tourbitz.api.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.tourbitz.api.domain.TourPlanInformation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TourPlanService {

  private Map<String, List<TourPlanInformation>> tourPlans;

  public TourPlanService() {

    this.tourPlans = new HashMap<>();
  }

  public List<TourPlanInformation> retreiveTourPlans(final String key) {
    log.info("Searching key -> " + key);
    if (this.tourPlans.get(key) != null) {
      return this.tourPlans.get(key);
    }
    return Collections.emptyList();
  }

  public void addTourPlan(String key, List<TourPlanInformation> tours) {
    this.tourPlans.put(key, tours);
  }
}
