package com.example.demo.domain.project.repository

interface FloorStructureRepositoryCustom {
    fun findTopKSimilarFloors(
        excludeProjectId: String,
        area: Double,
        aspectRI: Double,
        rectangularity: Double,
        k: Int,
    ): List<FloorStructureResult>
}