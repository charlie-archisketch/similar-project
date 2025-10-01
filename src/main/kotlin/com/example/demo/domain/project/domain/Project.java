package com.example.demo.domain.project.domain;

import com.example.demo.domain.project.domain.child.Portfolio;
import com.example.demo.domain.project.domain.child.Floorplan;
import com.example.demo.domain.project.domain.child.FromMap;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import org.springframework.lang.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor // todo: access level protected
@Document(collection = "projects")
public class Project implements Cloneable {

	@Id
	private String _id;

	@Indexed(name = "enterpriseId_1")
	private String enterpriseId;

	@NotBlank
	private String userId;

	@NotNull
	// 내 프로젝트 리스트
	private List<String> directoryIds = Lists.newArrayList();

	@NotNull
	// 팀 프로젝트 리스트
	private List<String> teamDirectoryIds = Lists.newArrayList();

	private Integer state; // 0: 생존, 1: 휴지통, 2: 삭제

	private String name;

	private String coverImage;

	private String coverRenderImageId;

	private String defaultCoverImage;

	private String floorplanPath;

	@Transient
	private List<Floorplan> floorplans = Lists.newArrayList();

	private List<String> imageIds;

	private List<Portfolio> portfolios;

	private List<String> portfolioIds = new ArrayList<>();

	private FromMap fromMap;

	@Nullable
	private String originalProjectId;

	private Boolean isOnAir = false;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	public void created() {
		LocalDateTime now = LocalDateTime.now();
		this.createdAt = now;
		this.updatedAt = now;
	}

	public void updated() {
		this.updatedAt = LocalDateTime.now();
	}

	public void setDefaultCoverImage(String defaultCoverImage) {
		if (defaultCoverImage != null) {
			this.defaultCoverImage = defaultCoverImage.trim();
		} else {
			this.defaultCoverImage = null;
		}
	}

	public void setName(String name) {
		if (name != null) {
			this.name = name.trim();
		} else {
			this.name = null;
		}
	}

	public void setCoverImage(String coverImage) {
		if (coverImage != null) {
			this.coverImage = coverImage.trim();
		} else {
			this.coverImage = null;
		}
	}

	public String getFloorplanKey() {
		return "projects/" + _id + "/floorplans.json";
	}

	@Override
	public Project clone() throws CloneNotSupportedException {
		return (Project) super.clone();
	}
}
