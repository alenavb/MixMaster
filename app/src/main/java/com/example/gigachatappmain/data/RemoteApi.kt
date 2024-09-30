package com.example.gigachatappmain.data

import com.example.gigachatappmain.domain.remote.Answer
import com.example.gigachatappmain.domain.remote.DataRaw
import com.example.gigachatappmain.domain.remote.Token
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Url

interface RemoteApi {
    @FormUrlEncoded
    @POST("oauth")
    suspend fun getToken(
        @Field("scope") scope: String = "GIGACHAT_API_PERS",
        @Header("Content-Type") contentType: String = "application/x-www-form-urlencoded",
        @Header("Accept") accept: String = "application/json",
        @Header("RqUID") id: String = "введите свой RqUID",
        @Header("Authorization") auth: String = "Введите свой Authorization"
    ): Token

    @POST()
    suspend fun getAnswer(
        @Url url : String,
        @Body dataRaw: DataRaw,
        @Header("Authorization") auth: String,
        @Header("Content-Type") contentType: String = "application/json",
        @Header("Accept") accept: String = "application/json"): Answer
}