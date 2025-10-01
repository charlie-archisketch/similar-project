package com.example.demo.domain.project.domain.child.floorplan;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PointLight extends Light {

    private Double radius;

    public PointLight() {
        super("point");
    }

    public void setData(PointLight data) {
        if(data == null) {
            return;
        }

        super.setData(data);

        if(data.getRadius() != null) {
            this.radius = data.getRadius();
        }
    }
}
