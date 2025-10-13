package com.example.demo.domain.project.repository

import com.example.demo.domain.project.domain.FloorStructure
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface FloorStructureResult {
    val id: String
    val title: String
    val projectId: String
    val score: Double
}

interface FloorStructureRepository: JpaRepository<FloorStructure, String> {

    @Query(
        value = """
        SELECT 
            id,
            title,
            project_id AS projectId,
            ( abs(area - :area)
            + abs(bounding_box_aspect_ri - :aspectRI)
            ) AS score
        FROM floor_structures
        WHERE project_id <> :excludeProjectId
        ORDER BY score ASC
        LIMIT :k
        """,
        nativeQuery = true
    )
    fun findTopKSimilarFloors(
        @Param("excludeProjectId") excludeProjectId: String,
        @Param("area") area: Double,
        @Param("aspectRI") aspectRI: Double,
        @Param("k") k: Int
    ): List<FloorStructureResult>
}