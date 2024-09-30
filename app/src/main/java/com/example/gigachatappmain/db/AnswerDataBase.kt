package com.example.gigachatappmain.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gigachatappmain.domain.db.DbAnswer
import com.example.gigachatappmain.domain.registration.RegUser

@Database(entities = [DbAnswer::class], version = 1)
abstract class AnswerDataBase: RoomDatabase(){
    abstract fun answerDao(): AnswerDao
}
@Database(entities = [RegUser::class], version = 1)
abstract class RegistrationDataBase: RoomDatabase(){
    abstract fun registrationDao(): RegDao
}