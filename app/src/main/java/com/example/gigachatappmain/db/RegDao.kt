package com.example.gigachatappmain.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.gigachatappmain.domain.registration.RegUser

@Dao
interface RegDao {
    @Query("SELECT * FROM RegUser")
    fun getRegUserAnswer(): List<RegUser>

    @Query("SELECT * FROM RegUser WHERE `login` LIKE :query LIMIT 1")
    suspend fun findByLogin(query: String): RegUser

    @Delete
    fun delete(user: RegUser)

    @Insert
    suspend fun insertUser(user: RegUser)

    @Query("SELECT * FROM RegUser WHERE `login` LIKE :login AND `password` like :pswd LIMIT 1")
    suspend fun findByLoginAndPassword(login: String, pswd: String): RegUser
}