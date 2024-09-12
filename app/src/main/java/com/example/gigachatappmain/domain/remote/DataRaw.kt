package com.example.gigachatappmain.domain.remote

import com.example.gigachatappmain.domain.db.Messages

data class DataRaw(
    val model: String,
    val messages: Array<Messages>,
    val temperature: Int,
    val top_p: Double,
    val n: Int,
    val stream: Boolean,
    val max_tokens: Int,
    val repetition_penalty: Int,
    val update_interval: Int
)
