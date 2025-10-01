package com.example.demo.domain.project.domain.child;

import com.example.demo.domain.project.domain.child.floorplan.*;
import com.example.demo.domain.project.domain.child.floorplan.Column;
import com.example.demo.domain.project.domain.child.floorplan.GroupV2;
import com.example.demo.domain.project.domain.child.floorplan.Room;
import com.example.demo.global.util.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import org.springframework.lang.Nullable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Floorplan {
	@Id
	private String id;

	private String archiId;

	private String title;

	private String floorplanImage;

	private Double floorplanImageScale;

	private FpImageScale fpImageScale;

	private Double area;

	private Dimension dimensions;

	private List<Corner> corners;

	private List<Wall> walls;

	private List<Point> points;

	private List<Line> lines;

	private List<Room> rooms;

	private List<Item> items;

	private List<FinishItem> finishItems;

	private List<Group> groups;

	private List<Column> columns;

	private List<GroupV2> groupsV2 = Collections.emptyList();

	private Object light;

	private List<LightSource> lightSources;

	@Nullable
	private String dxfUrl;

	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public Floorplan() {
		this.id = StringUtil.genObjectId();
	}

	public void created() {
		LocalDateTime now = LocalDateTime.now();
		this.createdAt = now;
		this.updatedAt = now;
	}

	public void updated() {
		this.updatedAt = LocalDateTime.now();
	}

	public List<Item> getItems() {
		if (this.items != null && this.items.size() > 3000) {
			// hotfix(24.12.20): floorplan 에 놓인 아이템 개수가 3000 개 보다 많으면 3000개 까지만 내려준다.
			return this.items.subList(0, 3000);
		}
		return this.items;
	}
}
