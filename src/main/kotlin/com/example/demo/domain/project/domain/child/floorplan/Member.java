package com.example.demo.domain.project.domain.child.floorplan;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Member {
    private String productId;

    private String archiId;

    private List<Double> matrix;

}
