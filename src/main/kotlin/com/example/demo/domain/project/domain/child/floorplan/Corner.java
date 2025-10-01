package com.example.demo.domain.project.domain.child.floorplan;

import com.example.demo.global.Transformation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Corner {

	private String archiId;

	private Transformation position;
}
