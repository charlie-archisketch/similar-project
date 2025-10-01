package com.example.demo.domain.project.domain.child.floorplan;

import com.example.demo.domain.materials.domain.child.Color;
import com.example.demo.domain.materials.domain.child.Texture;
import com.example.demo.domain.materials.domain.child.Vector2;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

@Getter
@Setter
public class Material {

  @Nullable
  String materialId;

  @Nullable
  Texture previewImage;

  @Nullable
  Texture colorImage;

  @Nullable
  Color color;

  @Nullable
  Texture normalImage;

  @Nullable
  Vector2 normalScale;

  @Nullable
  Texture roughnessImage;

  @Nullable
  Double roughness;

  @Nullable
  Texture metalImage;

  @Nullable
  Double metalness;

  @Nullable
  Boolean transparent;

  @Nullable
  Texture opacityImage;

  @Nullable
  Double opacity;

  @Nullable
  Texture displacementImage;

  @Nullable
  Double displacementScale;


  @Nullable
  Texture emissiveImage;


  @Nullable
  Double emissiveIntensity;
}
