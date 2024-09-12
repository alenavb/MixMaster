package com.example.gigachatappmain.presentation

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.gigachatappmain.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var answerViewModel: AnswerViewModel
    private lateinit var editTextMain: EditText
    private lateinit var buttonGenerateMain: AppCompatButton
    private lateinit var textResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        answerViewModel = ViewModelProvider(this).get(AnswerViewModel::class.java)

        editTextMain = findViewById(R.id.editTextMain)
        buttonGenerateMain = findViewById(R.id.buttonGenerateMain)
        textResult = findViewById(R.id.textResult)


        answerViewModel.result.observe(this, Observer { result ->
            textResult.text = result
        })

        buttonGenerateMain.setOnClickListener {
            val inputText = editTextMain.text.toString()
            if (inputText.isNotEmpty()) {
                val content  = "Придумай коктейль из этих ингридиентов + $inputText"
                answerViewModel.getResult(content)
            } else {
                Toast.makeText(this, "Пожалуйста, введите запрос", Toast.LENGTH_LONG)
            }
        }
    }
}