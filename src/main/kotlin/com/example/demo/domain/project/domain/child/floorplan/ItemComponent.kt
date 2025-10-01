package com.example.demo.domain.project.domain.child.floorplan

import com.example.demo.domain.project.domain.child.portfolio.Price
import com.fasterxml.jackson.annotation.JsonProperty
import java.awt.Color

data class ItemComponent(
	@JsonProperty("code") val code: String,
	@JsonProperty("namespace") val namespace: String,
	@JsonProperty("enterpriseCode") val enterpriseCode: String,
	@JsonProperty("relation") val relation: ComponentRelation?, // S3 에 넣을 땐 `null` 로 만들어 넣고, 다시 불러올 때 채워준다.
	@JsonProperty("component") val component: Component?, // S3 에 넣을 땐 `null` 로 만들어 넣고, 다시 불러올 때 채워준다.
	@JsonProperty("childComponents") val childComponents: List<ItemComponent>,
	@JsonProperty("quantity") val quantity: Double?, // 부품 갯수를 수정하지 않으면 null 로 남음.
) {
    fun isEqualTo(another: ItemComponent): Boolean {
        return this.code == another.code &&
            this.namespace == another.namespace &&
            this.enterpriseCode == another.enterpriseCode
    }
}

enum class ComponentGroupRule {
    MIN_MAX,
}

data class ComponentGroupInfo(
    val key: String,
    val name: String? = null,
    val rule: ComponentGroupRule,
    val ruleMin: Int,
    val ruleMax: Int,
)

data class ComponentRelation(
    val quantity: Double,
    val isRequired: Boolean,
    val groupInfo: ComponentGroupInfo?, // 어쩌면 다시 채워야할 수 있음. rule 이 변경될 수 있어서.
)

data class Component(
    val name: String?,
    val color: Color?,
    val mainMaterial: String?,
    val subMaterial: String?,
    val unit: String?,
    val dimension: Dimension?,
    val retailPrice: Price?,
)
