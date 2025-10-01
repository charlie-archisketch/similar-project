package com.example.demo.domain.project.domain.child.floorplan

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo


@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type",
)


@JsonSubTypes(
    JsonSubTypes.Type(value = ItemKitchenExtension::class, name = "Kitchen"),
    JsonSubTypes.Type(value = ItemBathExtension::class, name = "Bath"),
    JsonSubTypes.Type(value = ItemPartExtension::class, name = "Part"),
)

sealed class ItemExtension(
    val type: String,
)

class ItemKitchenExtension(type: String) : ItemExtension(type)

class ItemBathExtension(type: String) : ItemExtension(type)

class ItemPartExtension(type: String) : ItemExtension(type)





