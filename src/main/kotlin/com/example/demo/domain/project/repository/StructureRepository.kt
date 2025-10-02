package com.example.demo.domain.project.repository

import com.example.demo.domain.project.domain.Structure
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface StructureResult {
    val projectId: String
    val score: Double
}

interface StructureRepository: JpaRepository<Structure, String> {
    fun findByProjectId(projectId: String): Structure?

    @Query(
        value = """
        SELECT 
            project_id AS projectId,
            ( abs(area - :area)
            + abs(floor_count - :floorCount)
            + abs(bounding_box_aspect_ri - :aspectRI)
            ) AS score
        FROM structures
        WHERE project_id <> :excludeProjectId
        ORDER BY score ASC
        LIMIT :k
        """,
        nativeQuery = true
    )
    fun findTopKSimilarProjectIds(
        @Param("excludeProjectId") excludeProjectId: String,
        @Param("area") area: Double,
        @Param("floorCount") floorCount: Int,
        @Param("aspectRI") aspectRI: Double,
        @Param("k") k: Int
    ): List<StructureResult>
}