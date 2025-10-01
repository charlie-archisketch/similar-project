package com.example.demo.domain.project.domain.child.floorplan

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

data class Surface(
	val archiId: String,
	val points: Map<String, SurfacePoint>,
	val lines: Map<String, SurfaceLine>,
	val areas: Map<String, SurfaceArea>,
	val guidelines: Map<String, SurfaceGuideline>,
	val thickness: Double,
)

data class SurfacePoint(
    val archiId: String,
    val type: String,
    val coordinates: Position2D,
)

data class SurfaceLine(
    val archiId: String,
    val type: String,
    val start: String,
    val end: String,
    val arc: SurfaceArc?,
    val properties: SurfaceLineProperties?,
)

data class SurfaceArc(
    val center: Position2D,
    val radius: Double,
)

data class SurfaceLineProperties(
	val lightStrip: SurfaceLightStrip?,
	val lightTrough: SurfaceLightTrough?,
	val moldings: List<SurfaceMolding>?,
	@field:JsonProperty("isInitialShape")
	val isInitialShape: Boolean?,
	@field:JsonProperty("isInitialHole")
	val isInitialHole: Boolean?,
)

data class SurfaceLightStrip(
	val archiId: String,
	val type: String,
	val brightness: Double,
	val color: String,
	val radius: Double,
)

data class SurfaceLightTrough(
	val archiId: String,
	val path: SurfacePolygon,
)

data class SurfaceDeserializedLine(
	val archiId: String,
	val type: String,
	val start: SurfacePoint,
	val end: SurfacePoint,
	val arc: SurfaceArc?,
)

data class SurfacePolygon(
	val archiId: String,
	val type: String,
	val shape: List<SurfaceDeserializedLine>,
	val holes: List<List<SurfaceDeserializedLine>>,
)

data class SurfaceMolding(
	val archiId: String,
	val placement: String,
	val productId: String,
	val meta: Any? = null,
	val pathTransformation: SurfaceTransformation,
	val finishes: List<SurfaceFinish>,
)

data class SurfaceTransformation(
	val offset: Position2D,
	val rotation: Double,
	val scale: Position2D,
)

data class SurfaceArea(
    val archiId: String,
    val type: String,
	val shape: List<String>,
	val holes: List<List<String>>,
	val properties: SurfaceAreaProperties,
)

data class SurfaceAreaProperties(
    val finish: SurfaceFinish,
    val extrusion: SurfaceAreaExtrusion,
)

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type",
)
@JsonSubTypes(
    JsonSubTypes.Type(value = SurfaceColorFinish::class, name = "color"),
    JsonSubTypes.Type(value = SurfaceProductFinish::class, name = "product"),
)
abstract class SurfaceFinish (
    val archiId: String,
)

class SurfaceColorFinish(
    archiId: String,
    val color: String,
) : SurfaceFinish(archiId)

class SurfaceProductFinish(
    archiId: String,
    val productId: String,
    val meta: Any? = null,
	val transformation: SurfaceTransformation,
) : SurfaceFinish(archiId)

data class SurfaceAreaExtrusion(
	val value: Double,
)

data class SurfaceGuideline(
	val archiId: String,
	val type: String,
	val start: SurfacePoint,
	val end: SurfacePoint,
)
