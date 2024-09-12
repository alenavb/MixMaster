package com.example.gigachatappmain.domain.repository

import com.example.gigachatappmain.data.RemoteApi
import com.example.gigachatappmain.domain.remote.DataRaw
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(private val service : RemoteApi) {
    suspend fun getToken() = service.getToken()
    suspend fun getAnswer(url : String, dataRaw: DataRaw, token:String) = service.getAnswer(url, dataRaw, token)
}