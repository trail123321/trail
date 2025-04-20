package com.example.phonepeclone.models

data class Transaction(
    val id: String,
    val name: String,
    val amount: Double,
    val date: String,
    val type: Int
) {
    companion object {
        const val TYPE_PAID = 0
        const val TYPE_RECEIVED = 1
    }
} 