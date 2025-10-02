package com.example.demo.domain.project.domain.child.floorplan

import com.example.demo.global.Transformation
import java.util.*

data class Room(
    val archiId: String = UUID.randomUUID().toString(),
    val templateId: String? = null,
    val corners: List<String> = emptyList(),
    val height: Double = 1300.0,
    val level: Double? = 0.0,
    val label: String = "",
    val type: Int = 0, // 0: Untitled
    val hideCeiling: Boolean = false,
    val finish: Finish = Finish(),
    val ceiling: Finish = Finish(),
    val innerPoints: List<Transformation> = emptyList(),
    val lock: Boolean = false,
    val visible: Boolean? = true,
    val items: List<Item> = emptyList(),
    val seats: Int = 0,
    val area: Double? = null,
)
