package com.example.demo.domain.project.domain.child.floorplan;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Wall implements Cloneable {

  private String archiId = null;

  private List< String > corners;

  private Double height;

  private Double level = 0.0;

  private Double thickness;

  private List<Finish> finishes;

  public Wall() {

  }

  public Wall(List< Finish > finishes) {
    this.finishes = finishes;
  }

  public Wall(
      String archiId, List< String > corners, Double height, Double thickness, Double level,
      List< Finish > finishes
  ) {
    this.archiId = archiId;
    this.corners = corners;
    this.height = height;
    this.thickness = thickness;
    this.finishes = finishes;
    this.level = level;
  }

    public Wall clone() throws CloneNotSupportedException {
    Wall a = (Wall) super.clone();
    return a;
  }

}
