package com.example.reportarmais

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast

class pagPrincipal : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.)

    }

    fun validarLogin(view: View) {

        val email = findViewById<EditText>(R.id.editText).text.toString().trim()
        val pwd = findViewById<EditText>(R.id.editText2).text.toString().trim()

        if (email.isNullOrEmpty() || pwd.isNullOrEmpty()) {

            Toast.makeText(this, "Não vais passar", Toast.LENGTH_SHORT).show()

        }

    }

    //Tentar juntar o hotfix

    fun irParaPagNotas(view: View) {

        val intent = Intent(this, PagNotas::class.java)

        startActivity(intent)

    }
}
