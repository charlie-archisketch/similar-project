package com.example.demo.domain.project.domain

import com.example.demo.domain.project.domain.child.structure.BoundingBox
import jakarta.persistence.AttributeOverride
import jakarta.persistence.AttributeOverrides
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "room_structures")
data class RoomStructure(
    @Id
    val id: String,

    @Column(name = "project_id", nullable = false)
    val projectId: String,

    @Column(nullable = false)
    val type: Int,

    @Column(nullable = false)
    val area: Double,

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

    val rectangularity: Double,
)