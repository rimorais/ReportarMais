package com.example.reportarmais

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.example.reportarmais.Nota
import com.example.reportarmais.NotaViewModel
import kotlinx.android.synthetic.main.activity_nota_crud.*

class NotaCrud : AppCompatActivity() {

    private lateinit var notaViewModel: NotaViewModel
    private var textoId: Long = 0L
    private var isEdit: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Carregar o layout
        setContentView(R.layout.activity_nota_crud)

        val actionbar = supportActionBar
        actionbar!!.setDisplayHomeAsUpEnabled(true)

        /* Buscar o ViewModel definido em (`DataRecordViewModel::class.java`)
           do serviço ViewModelProvider.
        */
        notaViewModel = ViewModelProvider(this).get(NotaViewModel::class.java)

        /* Nota Importante -------------------------------------------------------------------- */
        /* Check Intent for Extra Data, based on the predefined key (which is the same that we use
           to insert such data. Also notice that since we expect it to be of type Long, we use
           `getLongExtra`. There's getters for different data types.
        */
        if (intent.hasExtra(Constantes.NOTA_ID)) {
            textoId = intent.getLongExtra(Constantes.NOTA_ID, 0L)

            /*  In this case, since the DAO `get(id)` method returns a `LiveData` object,
                we use the `observer` pattern to populate the view.
             */
            notaViewModel.get(textoId).observe(this, Observer {

                // Ger references to the UI items in the layout
                val viewId = findViewById<TextView>(R.id.nota_id)
                val viewRecord = findViewById<EditText>(R.id.nota_record)

                // Protect from null, which occurs when we delete the item
                if (it != null) {
                    // populate with data
                    viewId.text = it.id.toString()
                    viewRecord.setText(it.texto)
                }
            })
            isEdit = true
        }

        /* ------------------------------------------------------------------------------------ */

        /*

        Definir os onClickListeners para as operações de Gravar, Eliminar e Alterar.
        Isto utiliza as funções no ViewModel.

         */
        val btnSave = btnSave
        btnSave.setOnClickListener { view ->
            val id = 0L
            val record = nota_record.text.toString()

            if (record.isBlank() or record.isEmpty()) {
                Snackbar.make(view, R.string.empty_not_saved, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            } else {
                val item = Nota(id = id, texto = record)
                notaViewModel.insert(item)
                finish()
            }
        }

        val btnUpdate = btnUpdate
        btnUpdate.setOnClickListener { view ->
            val id = nota_id.text.toString().toLong()
            val record = nota_record.text.toString()

            if (record.isBlank() or record.isEmpty()) {
                Snackbar.make(view, R.string.empty_not_saved, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            } else {
                val item = Nota(id = id, texto = record)
                notaViewModel.update(item)
                finish()
            }
        }

        val btnDelete = btnDelete
        btnDelete.setOnClickListener {
            val id = nota_id.text.toString().toLong()
            val record = nota_record.text.toString()

            val item = Nota(id = id, texto = record)
            notaViewModel.delete(item)
            finish()
        }

        /*

        Esconder os botões dependendo da operação que está a ser realizada.

         */
        if (isEdit) {

            btnSave.visibility = View.GONE
        } else {

            btnUpdate.visibility = View.GONE
            btnDelete.visibility = View.GONE
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()

        val intent = Intent(this, pagNotas::class.java)

        startActivity(intent)

        return true
    }

}
