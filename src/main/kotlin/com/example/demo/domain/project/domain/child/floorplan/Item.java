package com.example.demo.domain.project.domain.child.floorplan;

import com.example.demo.global.Transformation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class Item {

  private String archiId;

  private String productId;

  private String groupId;

  private Object meta;

	@Nullable
	private String parametricInfo;

	private Transformation position;

  private Transformation rotation;

  private Transformation scale;

  private Transformation pivot;

  @Nullable
  private List< Double > localTransform; // GroupV2 에서 사용됨. 그룹 내의 local transformation matrix 를 저장함.

  private String nickname;

  private Boolean lock;

  private Boolean visible;

  private Object ext;

  private List<ItemComponent> components;

  @Nullable
  private String customPath;

  @Nullable
  private List<MaterialSnapshot> activedMaterials;

  public Item(
	  String archiId, String productId, String groupId, Object meta, String parametricInfo, Transformation position,
      Transformation rotation, Transformation scale, @Nullable List< Double > localTransform,
      Transformation pivot, String nickname, Boolean lock, Boolean visible, Object ext,
      @Nullable String customPath, @Nullable List< MaterialSnapshot > activedMaterials
  ) {
    this.archiId = archiId;
    this.productId = productId;
    this.groupId = groupId;
    this.meta = meta;
	  this.parametricInfo = parametricInfo;
    this.position = position;
    this.rotation = rotation;
    this.scale = scale;
    this.localTransform = localTransform;
    this.pivot = pivot;
    this.nickname = nickname;
    this.lock = lock;
    this.visible = visible;
    this.ext = ext;
    this.customPath = customPath;
    this.activedMaterials = activedMaterials;
  }

}
