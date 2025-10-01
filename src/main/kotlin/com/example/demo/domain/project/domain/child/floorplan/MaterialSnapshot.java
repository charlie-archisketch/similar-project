package com.example.demo.domain.project.domain.child.floorplan;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MaterialSnapshot {

  private String uuid;

  private String materialId;

  private Material material;

  private Boolean original;

  public void setData(MaterialSnapshot data) {
    this.uuid = data.getUuid();
    this.materialId = data.getMaterialId();
    this.material = data.getMaterial();
    this.original = data.getOriginal();
  }
}
