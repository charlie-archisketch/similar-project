package com.example.demo.domain.project.domain.child.floorplan;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Position2D {
	private Double x;

    private Double y;

    public void setData(Position2D data) {
        if(data == null) {
            return;
        }
        if(data.getX() != null) {
            this.x = data.getX();
        }
        if(data.getY() != null) {
            this.y = data.getY();
		}
    }

}
