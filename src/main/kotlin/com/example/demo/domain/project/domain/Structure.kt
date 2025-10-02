package com.example.demo.domain.project.domain

import com.example.demo.domain.project.domain.child.floorplan_structure.BoundingBox
import com.example.demo.global.util.StringUtil
import jakarta.persistence.*

@Entity
@Table(
    name = "structures",
    uniqueConstraints = [UniqueConstraint(name = "ux_structure_project", columnNames = ["project_id"])]
)
data class Structure(

    @Id
    val id: String = StringUtil.genId(),

    @Column(name = "project_id", nullable = false)
    val projectId: String,

    @Column(nullable = false)
    val area: Double,

    @Column(nullable = false)
    val floorCount: Int,

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
            name = "aspect",
            column = Column(name = "bounding_box_aspect", nullable = false)
        ),
        AttributeOverride(
            name = "aspectRI",
            column = Column(name = "bounding_box_aspect_ri", nullable = false)
        )
    )
    val boundingBox: BoundingBox,

//    val rooms: RoomStructure,
//
//    val orthogonality: Double, // 벽이 직각인 정도(0~1, 1에 가까울수록 직각에 가까움)의 평균
)