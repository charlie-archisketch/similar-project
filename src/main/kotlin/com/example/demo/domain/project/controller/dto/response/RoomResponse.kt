package com.example.demo.domain.project.controller.dto.response

import com.example.demo.domain.project.domain.Project
import java.time.LocalDateTime

data class RoomResponse(
    val id: String,
    val projectId: String,
    val projectName: String,
    val coverImage: String? = null,
    val defaultCoverImage: String? = null,
    val userId: String,
    val state: Int? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
) {
    companion object {
        @JvmStatic
        fun of(
            project: Project,
            roomId: String,
        ): RoomResponse {
            return RoomResponse(
                id = roomId,
                projectId = project._id,
                projectName = project.name,
                coverImage = project.coverImage,
                defaultCoverImage = project.defaultCoverImage,
                userId = project.userId,
                state = project.state,
                createdAt = project.createdAt,
                updatedAt = project.updatedAt,
            )
        }
    }
}