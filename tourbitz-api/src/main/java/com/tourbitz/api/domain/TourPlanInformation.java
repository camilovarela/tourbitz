package com.tourbitz.api.domain;

import java.util.List;
import lombok.Data;

@Data
public class TourPlanInformation {

  private String title;
  
  private String overview;
  
  private String price;
  
  private String image;
  
  private List<String> about;
}
