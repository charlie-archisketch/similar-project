package com.example.demo.domain.project.domain.child.floorplan;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Spotlight extends Light {

    private String iesTexture;

    private Euler direction;

    public Spotlight() {
        super("spot");
    }

    public void setData(Spotlight data) {
        if(data == null) {
            return;
        }

        super.setData(data);

        if(data.getIesTexture() != null) {
            this.iesTexture = data.getIesTexture();
        }
        if(data.getDirection() != null) {
            this.direction = data.getDirection();
        }
    }
}
