package com.example.test.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.test.R
import com.example.test.model.Consumer
import com.example.test.repository.AuthRepository
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var authRepository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        authRepository = AuthRepository(application)
    }

    fun login(view: View) {
        lifecycleScope.launch {
            val email: TextInputEditText=findViewById(R.id.email)
            val password: TextInputEditText=findViewById(R.id.password)
            val consumer = Consumer(email.text.toString(), password.text.toString())
            authRepository.login(consumer)
        }
    }
}