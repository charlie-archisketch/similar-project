package com.example.demo.domain.project.domain.child.floorplan;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class Group {
	private String archiId;

	private Boolean published;

	private Dimension inboundBox;

	private List<Member> member;

	private RelationProduct relationProducts;

	private RelationProductV2 relationProductsV2;

	private List<Double> matrix;

	private String nickname;

	private Boolean lock;

	private Boolean visible;

	private String decriminator;
}
