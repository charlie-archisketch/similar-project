package com.example.demo.domain.project.domain.child.floorplan

import com.example.demo.global.Transformation

data class GroupV2(
    val archiId: String,
    val position: Transformation,
    val rotation: Transformation,
    val scale: Transformation,
    val groupId: String?,
    val lock: Boolean,
    val nickname: String? = "",
    val children: List<GroupChild>,
    val relationProductsV2: RelationProductV2?,
    val ext: Map<String, Any>?,
    val assemblyGroupInfo: AssemblyGroupInfo?,
)

enum class ChildType {
    ITEM, GROUP,
}

data class GroupChild(
    val archiId: String,
    val type: ChildType,
)
