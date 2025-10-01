package com.example.demo.domain.project.domain.child.floorplan;

import com.example.demo.global.Transformation;
import com.example.demo.global.util.StringUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Light {
    protected String id;

    protected String archiId;

    protected String type;

    protected Boolean status;

    protected Transformation position;

    protected String colorType;

    protected Integer colorTemp;

    protected Rgb colorRgb;

    protected Integer brightness;

    public Light(String type) {
        this.id = StringUtil.genObjectId();
        this.type = type;
    }

    public void setData(Light data) {
        if(data == null) {
            return;
        }
        if(data.getId() != null) {
            this.id = data.getId();
        }
        if(data.getArchiId() != null) {
            this.archiId = data.getArchiId();
        }
        if(data.getType() != null) {
            this.type = data.getType();
        }
        if(data.getStatus() != null) {
            this.status = data.getStatus();
        }
        if(data.getPosition() != null) {
            this.position = data.getPosition();
        }
        if(data.getColorType() != null) {
            this.colorType = data.getColorType();
        }
        if(data.getColorTemp() != null) {
            this.colorTemp = data.getColorTemp();
        }
        if(data.getColorRgb() != null) {
            this.colorRgb = data.getColorRgb();
        }
        if(data.getBrightness() != null) {
            this.brightness = data.getBrightness();
        }
    }

}
