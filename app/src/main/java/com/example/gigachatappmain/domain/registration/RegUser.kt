package com.example.gigachatappmain.domain.registration

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RegUser(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val login: String,
    val password: String,
    val settingsTraining: String,
    val settingsDiet: String
)
