package com.example.gigachatappmain.presentation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gigachatappmain.MyApp
import com.example.gigachatappmain.Utils
import com.example.gigachatappmain.db.DBRepository
import com.example.gigachatappmain.domain.db.DbAnswer
import com.example.gigachatappmain.domain.db.Messages
import com.example.gigachatappmain.domain.remote.DataRaw
import com.example.gigachatappmain.domain.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AnswerViewModel @Inject constructor(
    private val repository: MainRepository,
    private val dbRepository: DBRepository
) : ViewModel() {

    private val _result = MutableStateFlow<String>("")
    val result: StateFlow<String> = _result.asStateFlow()
    private val context = MyApp.appContext
    private val sharedPref =
        context.getSharedPreferences("user details", AppCompatActivity.MODE_PRIVATE)

    fun getAnswer(content: String) {
        val token = sharedPref.getString("token", null) ?: ""
        Log.d("ViewModel", "getAnswer $token")
        val msg = Messages("user", content)
        val dr = DataRaw("GigaChat", arrayOf(msg), 1, 0.1, 1, false, 512, 1, 0)

        viewModelScope.launch {
            try {
                val nAnswer: DbAnswer? = dbRepository.getAnswer(content)
                if (nAnswer != null) {
                    Log.d("Mode", "Db")
                    _result.value = nAnswer.answer
                } else {
                    val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
                    val currentDate = sdf.format(Date())

                    val answer = repository.getAnswer(
                        "https://gigachat.devices.sberbank.ru/api/v1/chat/completions",
                        dr,
                        "Bearer $token"
                    ).choices[0].message.content

                    _result.value = answer

                    val dbAnswer = DbAnswer(currentDate.hashCode().toInt(), content, answer)
                    dbRepository.insertAnswer(dbAnswer)
                    Log.d("Mode", "Net")
                }
            } catch (e: IOException) {
                _result.value = "getAnswer: " + e.message.toString()
            } catch (e: HttpException) {
                _result.value = "getAnswer: " + e.message.toString()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getResult(content: String) {
        if (Utils.needToken()) {
            viewModelScope.launch {
                try {
                    val token = repository.getToken().access_token
                    sharedPref.edit().apply {
                        putString("token", token)
                        putString("timeToStartToken", LocalDateTime.now().toString())
                        apply()
                    }
                    Log.d("ViewModel", "getToken $token")
                    getAnswer(content)
                } catch (e: IOException) {
                    _result.value = "getToken: " + e.message.toString()
                } catch (e: HttpException) {
                    _result.value = "getToken: " + e.message.toString()
                }
            }
        } else {
            getAnswer(content)
        }
    }
}