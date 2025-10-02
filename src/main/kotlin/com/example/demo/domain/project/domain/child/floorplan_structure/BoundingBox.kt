package com.example.demo.domain.project.domain.child.floorplan_structure

import com.example.demo.domain.project.domain.child.Floorplan
import jakarta.persistence.Embeddable

@Embeddable
data class BoundingBox(
    val width: Double,
    val height: Double,
    val aspect: Double, // 종횡비
    val aspectRI: Double, // 종횡비(회전불변)
) {
    companion object {
        fun fromFloorplan(floorplan: Floorplan): BoundingBox {
            val corners = floorplan.corners

            require(corners.isNotEmpty()) { "Corners must not be empty" }

            val xs = corners.mapNotNull { it.position.x }
            val zs = corners.mapNotNull { it.position.z }

            val minX = xs.minOrNull() ?: 0.0
            val maxX = xs.maxOrNull() ?: 0.0
            val minZ = zs.minOrNull() ?: 0.0
            val maxZ = zs.maxOrNull() ?: 0.0

            val width = maxX - minX
            val height = maxZ - minZ
            val aspect = if (height != 0.0) width / height else Double.POSITIVE_INFINITY
            val aspectRI = if (width != 0.0 && height != 0.0) {
                maxOf(width, height) / minOf(width, height)
            } else {
                Double.POSITIVE_INFINITY
            }

            return BoundingBox(
                width = width,
                height = height,
                aspect = aspect,
                aspectRI = aspectRI,
            )
        }
    }
}