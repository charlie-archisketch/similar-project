package com.example.demo.domain.project.repository

interface RoomStructureRepositoryCustom {
    fun findTopKSimilarRooms(
        excludeProjectId: String,
        area: Double,
        areaFrom: Double,
        areaTo: Double,
        rectangularity: Double,
        aspectRI: Double,
        k: Int
    ): List<RoomStructureResult>

    fun findTopKSimilarRoomsByType(
        excludeProjectId: String,
        area: Double,
        areaFrom: Double,
        areaTo: Double,
        rectangularity: Double,
        aspectRI: Double,
        type: Int,
        k: Int
    ): List<RoomStructureResult>
}