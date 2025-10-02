package com.example.demo.domain.project.domain.child.floorplan_structure

data class RoomStructure(
    val roomCounts: Int,
    val cornerHistogram: CornerHistogram,
)

data class CornerHistogram(
    val _3: Int,
    val _4: Int,
    val _5: Int,
    val _6Plus: Int,
)
