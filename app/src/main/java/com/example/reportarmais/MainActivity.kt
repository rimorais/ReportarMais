package com.example.reportarmais

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun irParaNotas(view: View) {

        val intent = Intent(this, pagNotas::class.java)

        startActivity(intent)

    }
}
