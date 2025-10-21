package com.example.demo.domain.project.repository

import com.example.demo.domain.project.domain.QFloorStructure.floorStructure
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class FloorStructureRepositoryImpl(
    val queryFactory: JPAQueryFactory
) : FloorStructureRepositoryCustom {
    override fun findTopKSimilarFloors(
        excludeProjectId: String,
        area: Double,
        aspectRI: Double,
        rectangularity: Double,
        k: Int,
    ): List<FloorStructureResult> {
        val areaDist = floorStructure.area.subtract(area).abs().divide(area)
        val aspectDist = floorStructure.boundingBox.aspectRI.subtract(aspectRI).abs().divide(0.3)
        val rectangularityDist = floorStructure.rectangularity.subtract(rectangularity).abs().divide(0.1)

        val scoreExpr =
            areaDist.multiply(0.3)
                .add(aspectDist.multiply(0.5))
                .add(rectangularityDist.multiply(0.2))

        return queryFactory
            .select(
                Projections.constructor(
                    FloorStructureResult::class.java,
                    floorStructure.id,
                    floorStructure.title,
                    floorStructure.projectId,
                    scoreExpr,
                )
            )
            .from(floorStructure)
            .where(floorStructure.projectId.ne(excludeProjectId))
            .orderBy(scoreExpr.asc())
            .limit(k.toLong())
            .fetch()
    }
}

data class FloorStructureResult(
    val id: String,
    val title: String,
    val projectId: String,
    val score: Double,
)