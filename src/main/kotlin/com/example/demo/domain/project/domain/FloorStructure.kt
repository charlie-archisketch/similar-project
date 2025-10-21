package com.example.demo.domain.project.domain

import com.example.demo.domain.project.domain.child.structure.BoundingBox
import jakarta.persistence.*

@Entity
@Table(name = "floor_structures")
data class FloorStructure(

    @Id
    val id: String,

    @Column(nullable = false)
    val title: String,

    @Column(name = "project_id", nullable = false)
    val projectId: String,

    @Column(nullable = false)
    val area: Double,

//    @Column(nullable = false)
//    val roomCount: Int,

    @Embedded
    @AttributeOverrides(
        AttributeOverride(
            name = "width",
            column = Column(name = "bounding_box_width", nullable = false)
        ),
        AttributeOverride(
            name = "height",
            column = Column(name = "bounding_box_height", nullable = false)
        ),
        AttributeOverride(
            name = "area",
            column = Column(name = "bounding_box_area", nullable = false)
        ),
        AttributeOverride(
            name = "aspect",
            column = Column(name = "bounding_box_aspect", nullable = false)
        ),
        AttributeOverride(
            name = "aspectRI",
            column = Column(name = "bounding_box_aspect_ri", nullable = false)
        )
    )
    val boundingBox: BoundingBox,

    @Column(nullable = false)
    val rectangularity: Double, // 직사각형인 정도
)
