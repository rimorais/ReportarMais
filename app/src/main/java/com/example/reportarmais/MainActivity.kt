package com.example.reportarmais

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast

const val EXTRA_MESSAGE = "username"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val SharedPref: SharedPreferences = getSharedPreferences(

            getString(R.string.spUm), Context.MODE_PRIVATE

        )

        val logar = SharedPref.getBoolean(getString(R.string.spLogado), false)

        val nome = SharedPref.getString(getString(R.string.spUsername), "Nome")

        if (logar) {

            val intent = Intent(this, MapsActivity::class.java).apply {

                putExtra(EXTRA_MESSAGE, nome)

            }

            startActivity(intent)

        }

    }

    fun irParaNotas(view: View) {

        val intent = Intent(this, pagNotas::class.java)

        startActivity(intent)

    }

    fun irParaMapa(view: View) {

        var us = findViewById<EditText>(R.id.editText)
        var pw = findViewById<EditText>(R.id.editText2)
        var ust = us.text.toString()
        var pwt = pw.text.toString()

        if (ust.isNullOrEmpty() || pwt.isNullOrEmpty()) {

            Toast.makeText(this, R.string.empty_not_saved, Toast.LENGTH_LONG).show()

        }
        else {

            val SharedPref: SharedPreferences = getSharedPreferences(

                getString(R.string.spUm), Context.MODE_PRIVATE

            )

            with (SharedPref.edit()) {

                putBoolean(getString(R.string.spLogado), true)

                putString(getString(R.string.spUsername), ust)

                commit()

            }

            val intent = Intent(this, MapsActivity::class.java).apply {

                putExtra(EXTRA_MESSAGE, ust)

            }

            startActivity(intent)

        }

    }
}
