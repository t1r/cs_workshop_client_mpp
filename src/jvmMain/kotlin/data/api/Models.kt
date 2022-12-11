package data.api

import kotlinx.serialization.Serializable

@Serializable
data class FoodModel(
    val id: Long,
    val name: String,
    val weight: Float,
)
