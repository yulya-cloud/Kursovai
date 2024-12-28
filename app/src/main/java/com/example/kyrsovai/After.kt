package com.example.kyrsovai

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class After : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_after)
    }
    fun BackBegin(v: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}