package com.example.gigachatappmain.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.gigachatappmain.R
import com.example.gigachatappmain.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var answerViewModel: AnswerViewModel
    private lateinit var mBind: ActivityMainBinding

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBind.root)

        answerViewModel = ViewModelProvider(this).get(AnswerViewModel::class.java)

        lifecycleScope.launchWhenStarted {
            answerViewModel.result.collect { result ->
                mBind.textResult.text = result
            }
        }

        mBind.buttonGenerateMain.setOnClickListener {
            val inputText = mBind.editTextMain.text.toString()
            if (inputText.isNotEmpty()) {
                val content  = "Придумай коктейль из этих ингридиентов + $inputText"
                answerViewModel.getResult(content)

            } else {
                Toast.makeText(this, "Пожалуйста, введите запрос", Toast.LENGTH_LONG)
            }
        }
    }
}