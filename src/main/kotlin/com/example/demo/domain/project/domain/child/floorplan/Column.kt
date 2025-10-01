package com.example.demo.domain.project.domain.child.floorplan

import com.archisketch.dashboard.domain.project.enums.ColumnType
import com.example.demo.global.Transformation

data class Column(
    val archiId: String,
    val position: Transformation,
    val type: ColumnType,
    val rotation: Transformation,
    val scale: Transformation,
    val lock: Boolean,
    val visible: Boolean,
    val planes: List<Plane>,

    )

data class Plane(
    val archiId: String,
    val finish: Finish,
)

