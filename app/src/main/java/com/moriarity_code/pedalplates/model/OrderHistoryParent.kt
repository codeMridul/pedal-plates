package com.moriarity_code.pedalplates.model

data class OrderHistoryParent(
    val order_id: String,
    val restaurant_name: String,
    val total_cost: String,
    val order_placed_at: String,
    val food_items: ArrayList<OrderHistoryChild>
)