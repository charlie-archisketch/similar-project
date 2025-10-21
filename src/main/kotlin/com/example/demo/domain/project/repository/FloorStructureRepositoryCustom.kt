package com.example.demo.domain.project.repository

interface FloorStructureRepositoryCustom {
    fun findTopKSimilarFloors(
        excludeProjectId: String,
        area: Double,
        areaFrom: Int,
        areaTo: Int,
        aspectRI: Double,
        rectangularity: Double,
        k: Int,
    ): List<FloorStructureResult>
}