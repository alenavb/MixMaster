package com.example.gigachatappmain.domain.db

data class Choices(
    val message: Messages,
    val index: Int,
    val finish_reason: String
)

