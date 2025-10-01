package com.example.demo.domain.project.domain.child.floorplan;


import com.example.demo.global.Transformation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Dimension {
    private Transformation min;

    private Transformation max;
}

