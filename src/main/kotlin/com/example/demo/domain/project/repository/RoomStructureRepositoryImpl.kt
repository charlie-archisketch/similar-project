package com.example.demo.domain.project.repository

import com.example.demo.domain.project.domain.QRoomStructure.roomStructure
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class RoomStructureRepositoryImpl(
    val queryFactory: JPAQueryFactory,
) : RoomStructureRepositoryCustom {
    override fun findTopKSimilarRooms(
        excludeProjectId: String,
        area: Double,
        areaFrom: Double,
        areaTo: Double,
        rectangularity: Double,
        aspectRI: Double,
        k: Int,
    ): List<RoomStructureResult> {
        // Normalization
        val areaDist = roomStructure.area.subtract(area).abs().divide(area)
        val aspectDist = roomStructure.boundingBox.aspectRI.subtract(aspectRI).abs().divide(0.3)
        val rectangularityDist = roomStructure.rectangularity.subtract(rectangularity).abs().divide(0.1)

        val scoreExpr =
            areaDist.multiply(0.3)
                .add(aspectDist.multiply(0.5))
                .add(rectangularityDist.multiply(0.2))

        return queryFactory
            .select(
                Projections.constructor(
                    RoomStructureResult::class.java,
                    roomStructure.id,
                    roomStructure.projectId,
                    scoreExpr,
                )
            )
            .from(roomStructure)
            .where(
                roomStructure.projectId.ne(excludeProjectId),
                roomStructure.area.between(areaFrom, areaTo),
                roomStructure.rectangularity.between(
                    rectangularity - 0.1,
                    rectangularity + 0.1,
                )
            )
            .orderBy(scoreExpr.asc())
            .limit(k.toLong())
            .fetch()
    }

    override fun findTopKSimilarRoomsByType(
        excludeProjectId: String,
        area: Double,
        areaFrom: Double,
        areaTo: Double,
        rectangularity: Double,
        aspectRI: Double,
        type: Int,
        k: Int,
    ): List<RoomStructureResult> {
        // Normalization
        val areaDist = roomStructure.area.subtract(area).abs().divide(area)
        val aspectDist = roomStructure.boundingBox.aspectRI.subtract(aspectRI).abs().divide(0.3)
        val rectangularityDist = roomStructure.rectangularity.subtract(rectangularity).abs().divide(0.1)

        val scoreExpr =
            areaDist.multiply(0.3)
                .add(aspectDist.multiply(0.5))
                .add(rectangularityDist.multiply(0.2))

        return queryFactory
            .select(
                Projections.constructor(
                    RoomStructureResult::class.java,
                    roomStructure.id,
                    roomStructure.projectId,
                    scoreExpr,
                )
            )
            .from(roomStructure)
            .where(
                roomStructure.projectId.ne(excludeProjectId),
                roomStructure.type.eq(type),
                roomStructure.area.between(areaFrom, areaTo),
                roomStructure.rectangularity.between(
                    rectangularity - 0.1,
                    rectangularity + 0.1,
                )
            )
            .orderBy(scoreExpr.asc())
            .limit(k.toLong())
            .fetch()
    }
}

data class RoomStructureResult(
    val id: String,
    val projectId: String,
    val score: Double,
)