package com.example.test.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.test.R

class OptionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)

        findViewById<Button>(R.id.btnSubmit2).setOnClickListener {
            startActivity(Intent(this, CreateIncidentActivity::class.java))
        }
    }
}