package com.example.test.ui

import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.test.R

class OptionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)

        findViewById<Button>(R.id.btnSubmit).setOnClickListener {
            startActivity(Intent(this, ListPccActivity::class.java))
        }

        findViewById<Button>(R.id.btnSubmit2).setOnClickListener {
            startActivity(Intent(this, CreatePccActivity::class.java))
        }
    }
}