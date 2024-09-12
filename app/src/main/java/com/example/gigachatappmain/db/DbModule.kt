package com.example.gigachatappmain.db

import android.app.Application
import androidx.room.Room
import com.example.gigachatappmain.domain.repository.ADataBase
import com.example.gigachatappmain.domain.repository.RDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {
    @Singleton
    @Provides
    @ADataBase
    fun getDataBase(app: Application) = Room.databaseBuilder(app, AnswerDataBase::class.java, "Answer.db").build()


    @Singleton
    @Provides
    @RDataBase
    fun getRegistrationDataBase(app: Application) = Room.databaseBuilder(app, RegistrationDataBase::class.java, "RegUser.db").build()
}