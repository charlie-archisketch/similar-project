package com.example.demo.domain.project.controller.dto.response

import com.example.demo.domain.project.domain.Project
import java.time.LocalDateTime

data class FloorResponse(
    val _id: String,
    val userId: String,
    val floorId: String,
    val floorTitle: String,
    val name: String,
    val coverImage: String? = null,
    val defaultCoverImage: String? = null,
    val state: Int? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
) {
    companion object {
        @JvmStatic
        fun toResponse(
            project: Project,
            floorId: String,
            floorName: String,
        ): FloorResponse {
            return FloorResponse(
                _id = project._id,
                userId = project.userId,
                floorId = floorId,
                floorTitle = floorName,
                name = project.name,
                coverImage = project.coverImage,
                defaultCoverImage = project.defaultCoverImage,
                state = project.state,
                createdAt = project.createdAt,
                updatedAt = project.updatedAt,
            )
        }
    }
}