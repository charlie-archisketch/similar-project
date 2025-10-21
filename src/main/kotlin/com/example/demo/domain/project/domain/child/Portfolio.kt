package com.example.demo.domain.project.domain.child

import com.example.demo.domain.project.domain.enums.ShowFloorplan
import org.springframework.data.annotation.Id
import java.time.LocalDateTime

data class Portfolio(
    @Id
    val id: String,

    val title: String,

    val description: String? = null,

    val order: String,

    val imageIds: List<String> = emptyList(),

    val showFloorplan: ShowFloorplan = ShowFloorplan.None,

    val createdAt: LocalDateTime = LocalDateTime.now(),

    val updatedAt: LocalDateTime = LocalDateTime.now(),
)