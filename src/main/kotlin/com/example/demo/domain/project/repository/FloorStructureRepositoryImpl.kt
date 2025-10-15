package com.example.demo.domain.project.repository

import com.example.demo.domain.project.domain.QFloorStructure.floorStructure
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.NumberExpression
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
        k: Int
    ): List<FloorStructureResult> {
        val scoreExpr: NumberExpression<Double> =
            floorStructure.area.subtract(area).abs()
                .add(floorStructure.boundingBox.aspectRI.subtract(aspectRI).abs())

        return queryFactory
            .select(
                Projections.constructor(
                    FloorStructureResult::class.java,
                    floorStructure.id,
                    floorStructure.title,
                    floorStructure.projectId,
                    scoreExpr
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