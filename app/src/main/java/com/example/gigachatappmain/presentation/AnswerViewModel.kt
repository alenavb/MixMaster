package com.example.gigachatappmain.presentation

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gigachatappmain.MyApp
import com.example.gigachatappmain.Utils
import com.example.gigachatappmain.db.DataBaseRepository
import com.example.gigachatappmain.domain.db.DbAnswer
import com.example.gigachatappmain.domain.db.Messages
import com.example.gigachatappmain.domain.remote.DataRaw
import com.example.gigachatappmain.domain.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AnswerViewModel @Inject constructor(
    val repository: MainRepository,
    val dbRepository: DataBaseRepository
) : ViewModel() {

    private val _result = MutableLiveData<String>()
    val result: LiveData<String> = _result
    val context = MyApp.appContext
    val sharedpref =
        context.getSharedPreferences(
            "user details",
            AppCompatActivity.MODE_PRIVATE
        )

    fun getAnswer(content: String) {
        val token = sharedpref.getString("token", null) ?: ""
        Log.d("ViewModel", "getAnswer $token")
        val msg = Messages("user", content)
        val dr = DataRaw("GigaChat", arrayOf(msg), 1, 0.1, 1, false, 512, 1, 0)

        viewModelScope.launch {
            try {
                val nAnswer: DbAnswer? = dbRepository.getAnswer(content)
                if (nAnswer != null) {
                    Log.d("Mode", "Db")
                    _result.postValue(nAnswer.answer)
                } else {
                    val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
                    val currentDate = sdf.format(Date())

                    val answer = repository.getAnswer(
                        "https://gigachat.devices.sberbank.ru/api/v1/chat/completions",
                        dr,
                        "Bearer $token"
                    ).choices[0].message.content

                    _result.postValue(answer)

                    val dbAnswer = DbAnswer(currentDate.hashCode().toInt(), content, answer)
                    dbRepository.insertAnswer(dbAnswer)
                    Log.d("Mode", "Net")
                }
            } catch (e: IOException) {
                _result.postValue("getAnswer: " + e.message.toString())
            } catch (e: HttpException) {
                _result.postValue("getAnswer: " + e.message.toString())
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getResult(content: String) {
        if (Utils.needToken()) {
            viewModelScope.launch {
                try {
                    val token = repository.getToken().access_token
                    val editor = sharedpref.edit()
                    editor.putString("token", token)
                    editor.putString("timeToStartToken", LocalDateTime.now().toString())
                    editor.apply()
                    Log.d("ViewModel", "getToken $token")
                    getAnswer(content)
                } catch (e: IOException) {
                    _result.postValue("getToken: " + e.message.toString())
                } catch (e: HttpException) {
                    _result.postValue("getToken: " + e.message.toString())
                }
            }
        } else {
            getAnswer(content)
        }
    }
}