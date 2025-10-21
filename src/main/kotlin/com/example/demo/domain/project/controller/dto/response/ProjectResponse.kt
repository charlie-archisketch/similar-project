package com.example.demo.domain.project.controller.dto.response

import com.example.demo.domain.project.domain.Project
import com.example.demo.global.ImageConverter
import java.time.LocalDateTime

data class ProjectResponse(
    val _id: String,
    val userId: String,
    val name: String,
    val enterpriseId: String? = null,
    val directoryIds: List<String> = emptyList(),
    val teamDirectoryIds: List<String> = emptyList(),
    val coverImage: String,
    val defaultCoverImage: String,
    val floorplanPath: String,
    val state: Int? = null,
    val bookmark: Boolean = false,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
) {
    companion object {
        @JvmStatic
        fun from(
            project: Project,
        ): ProjectResponse {
            return ProjectResponse(
                _id = project._id,
                userId = project.userId,
                name = project.name,
                enterpriseId = project.enterpriseId,
                directoryIds = project.directoryIds,
                teamDirectoryIds = project.teamDirectoryIds,
                coverImage = project.coverImage.let { ImageConverter.convertImageUrl(imageUrl = it, width = 512) },
                defaultCoverImage = project.defaultCoverImage.let {
                    ImageConverter.convertImageUrl(
                        imageUrl = it,
                        width = 512
                    )
                },
                floorplanPath = project.floorplanPath,
                state = project.state,
                createdAt = project.createdAt,
                updatedAt = project.updatedAt,
            )
        }
    }
}