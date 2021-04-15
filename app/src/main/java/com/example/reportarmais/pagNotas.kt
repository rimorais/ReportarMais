package com.example.reportarmais

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reportarmais.NotaAdapter
import com.example.reportarmais.NotaViewModel
import kotlinx.android.synthetic.main.activity_pag_notas2.*
import kotlinx.android.synthetic.main.nota_list_recyclerview.*

private const val TAG = "NotaListActivity"

class pagNotas : AppCompatActivity() {

    private lateinit var notaViewModel: NotaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Carregar o layout
        setContentView(R.layout.activity_pag_notas2)
        //setSupportActionBar(toolbar)

        // Definir uma ação para o FAB: vai começar uma nova atividade
        fab_add.setOnClickListener { _ ->
            val intent = Intent(this, NotaCrud::class.java)
            startActivity(intent)
        }

        /* Nota Importante --------------------------------------------------------------------- */
        /* Reference objects inside the loaded layout: notice that in Kotlin we can use short
        names (the IDE will prompt for imports).

        To be clear, the following line could have been:
           val recyclerView = R.id.datarecord_list

        But since the IDE has imported:
           import kotlinx.android.synthetic.main.activity_datarecord_list.*
           import kotlinx.android.synthetic.main.data_record_list_recyclerview.*

        we can simply write:
           val recyclerView = datarecord_list


        For every RecyclerView we always need to set:
        - An Adapter --> we define
        - A LayoutManager --> predefined, either Linear or Grid
         */
        /* ---------------------------------------------------------------------------------- */
        val recyclerView = nota_list
        val adapter = NotaAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        /* Popular a lista para isto vamos buscar o ViewModel
           já definido - `NotaViewModel::class.java`, do
           serviço ViewModelProvider.
        */
        notaViewModel = ViewModelProvider(this).get(NotaViewModel::class.java)

        /*

        Associar um Observer com cada um dos items no viewmodel.

        */
        notaViewModel.allItems.observe(this, Observer { items ->
            items?.let { adapter.setItems(it) }
        })

    }
}
