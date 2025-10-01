package com.example.demo.domain.materials.domain.child


data class Texture (
    val url : String,
    val repeat : Vector2,
    val offset: Vector2,
    val center : Vector2,
    val rotation: Double,
)