package com.example.demo.global;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Transformation {

    private Double x;

    private Double y;

    private Double z;

    public void setData(Transformation data) {
        if(data == null)
            return;
        if(data.getX() != null) {
            this.x = data.getX();
        }
        if(data.getY() != null) {
            this.y = data.getY();
        }
        if(data.getZ() != null) {
            this.z = data.getZ();
        }
    }

}
