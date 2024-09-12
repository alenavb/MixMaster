package com.example.gigachatappmain.domain.remote

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.gigachatappmain.domain.db.Choices
import com.example.gigachatappmain.domain.db.Usage

@Entity
data class Answer(
    val choices: Array<Choices>,
    @PrimaryKey(autoGenerate = true)
    val created: Long,
    val model: String,
    val aObject: String,
    val use: Usage
)

