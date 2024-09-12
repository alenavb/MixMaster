package com.example.gigachatappmain.db

import com.example.gigachatappmain.domain.db.DbAnswer
import com.example.gigachatappmain.domain.repository.ADataBase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataBaseRepository @Inject constructor (@ADataBase private val db: AnswerDataBase) {
    suspend fun getAnswer(query:String) = db.answerDao().findByQuery(query)

    suspend fun insertAnswer(answer: DbAnswer) = db.answerDao().insertAnswer(answer)
}
