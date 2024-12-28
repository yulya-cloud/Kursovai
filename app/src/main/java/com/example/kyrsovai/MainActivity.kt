package com.example.kyrsovai

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun startKurs(v: View) {
        val intent = Intent(this, Kurs::class.java)
        startActivity(intent)
    }
    fun startAfter(v: View) {
        val intent = Intent(this, After::class.java)
        startActivity(intent)
    }
}
