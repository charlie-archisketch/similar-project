package com.example.demo.domain.project.domain.child.structure

import com.example.demo.domain.project.domain.child.Floorplan
import com.example.demo.domain.project.domain.child.floorplan.Room
import jakarta.persistence.Embeddable

@Embeddable
data class BoundingBox(
    val width: Double,
    val height: Double,
    val area: Double,
    val aspect: Double, // 종횡비
    val aspectRI: Double, // 종횡비(회전불변)
) {
    companion object {
        fun fromFloorplan(floorplan: Floorplan): BoundingBox {
            val innerPoints = floorplan.rooms.flatMap { it.innerPoints }
                .toList()

            require(innerPoints.isNotEmpty()) { "Corners must not be empty" }

            val xs = innerPoints.mapNotNull { it.x }
            val zs = innerPoints.mapNotNull { it.z }

            val minX = xs.minOrNull() ?: 0.0
            val maxX = xs.maxOrNull() ?: 0.0
            val minZ = zs.minOrNull() ?: 0.0
            val maxZ = zs.maxOrNull() ?: 0.0

            val width = (maxX - minX) * 2
            val height = (maxZ - minZ) * 2
            val area = width * height
            val aspect = if (height != 0.0) width / height else Double.POSITIVE_INFINITY
            val aspectRI = if (width != 0.0 && height != 0.0) {
                maxOf(width, height) / minOf(width, height)
            } else {
                Double.POSITIVE_INFINITY
            }

            return BoundingBox(
                width = width,
                height = height,
                area = area,
                aspect = aspect,
                aspectRI = aspectRI,
            )
        }

        fun fromRoom(floorplan: Floorplan, room: Room): BoundingBox {
            val innerPoints = room.innerPoints

            require(innerPoints.isNotEmpty()) { "Inner points must not be empty" }
            require(floorplan.rooms.contains(room))

            val xs = innerPoints.map { it.x }
            val zs = innerPoints.map { it.z }

            val minX = xs.minOrNull() ?: 0.0
            val maxX = xs.maxOrNull() ?: 0.0
            val minZ = zs.minOrNull() ?: 0.0
            val maxZ = zs.maxOrNull() ?: 0.0

            val width = (maxX - minX) * 2
            val height = (maxZ - minZ) * 2
            val area = width * height
            val aspect = if (height != 0.0) width / height else Double.POSITIVE_INFINITY
            val aspectRI = if (width != 0.0 && height != 0.0) {
                maxOf(width, height) / minOf(width, height)
            } else {
                Double.POSITIVE_INFINITY
            }

            return BoundingBox(
                width = width,
                height = height,
                area = area,
                aspect = aspect,
                aspectRI = aspectRI,
            )
        }
    }
}