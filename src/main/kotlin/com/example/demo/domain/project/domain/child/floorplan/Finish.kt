package com.example.demo.domain.project.domain.child.floorplan

import org.bson.types.ObjectId

data class Finish(
    val id: String = ObjectId().toString(),
    var productId: String? = null,
    var color: String? = "ffffff",
    var meta: Any? = null,
    val scheme: Any? = null,
    val offset: Offset2D = Offset2D(),
    val rotation: Double = 0.0,
    val surface: Surface? = null,
)

data class Offset2D(
    val x: Double = 0.0,
    val y: Double = 0.0,
)
