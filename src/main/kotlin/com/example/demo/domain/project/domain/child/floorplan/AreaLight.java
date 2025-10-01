package com.example.demo.domain.project.domain.child.floorplan;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AreaLight extends Light {

    private Double width;

    private Double height;

    private Euler direction;

    private Boolean portal;

    public AreaLight() {
        super("area");
    }

    public void setData(AreaLight data) {
        if(data == null) {
            return;
        }

        super.setData(data);

        if(data.getWidth() != null) {
            this.width = data.getWidth();
        }
        if(data.getHeight() != null) {
            this.height = data.getHeight();
        }
        if(data.getDirection() != null) {
            this.direction = data.getDirection();
        }
        if(data.getPortal() != null) {
            this.portal = data.getPortal();
        }
    }
}
