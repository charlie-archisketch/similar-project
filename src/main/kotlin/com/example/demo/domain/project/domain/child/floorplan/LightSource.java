package com.example.demo.domain.project.domain.child.floorplan;

import com.example.demo.global.util.StringUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LightSource {

    private String id;

    private String title;

    private List<Object> items;

    public LightSource() {
        this.id = StringUtil.genObjectId();
    }
}
