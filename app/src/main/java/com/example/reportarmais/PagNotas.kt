package com.example.reportarmais

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.reportarmais.adapters.NotaAdapter
import com.example.reportarmais.entities.Nota
import com.example.reportarmais.viewModel.NotaViewModel
import com.example.reportarmais.viewModel.NotaViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class PagNotas : AppCompatActivity() {

    private val newNotaActivityRequestCode = 1
    private val notaViewModel: NotaViewModel by viewModels {
        NotaViewModelFactory((application as NotaApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = NotaAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.

        notaViewModel.allWords.observe(owner = this) { nota ->
            // Update the cached copy of the words in the adapter.
            nota.let { adapter.submitList(it) }
        }

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@PagNotas, PagAddNota::class.java)
            startActivityForResult(intent, newNotaActivityRequestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == newNotaActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.getStringExtra(PagAddNota.EXTRA_REPLY)?.let { reply ->
                val nota = Nota(reply)
                notaViewModel.insert(nota)
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.insertTitulo,
                Toast.LENGTH_LONG
            ).show()
        }

    }
}
