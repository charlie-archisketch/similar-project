package com.example.demo.domain.project.controller.dto.response

import com.example.demo.domain.project.domain.Project
import java.time.LocalDateTime

data class FloorResponse(
    val _id: String,
    val title: String,
    val projectId: String,
    val projectName: String,
    val userId: String,
    val coverImage: String? = null,
    val defaultCoverImage: String? = null,
    val state: Int? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
) {
    companion object {
        @JvmStatic
        fun of(
            project: Project,
            _id: String,
            title: String,
        ): FloorResponse {
            return FloorResponse(
                _id = _id,
                title = title,
                projectId = project._id,
                projectName = project.name,
                userId = project.userId,
                coverImage = project.coverImage,
                defaultCoverImage = project.defaultCoverImage,
                state = project.state,
                createdAt = project.createdAt,
                updatedAt = project.updatedAt,
            )
        }
    }
}