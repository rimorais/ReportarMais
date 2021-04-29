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

        // Load the layout
        setContentView(R.layout.activity_pag_notas2)
        //setSupportActionBar(toolbar)

        val actionbar = supportActionBar
        actionbar!!.setDisplayHomeAsUpEnabled(true)

        // Set an action for the FAB: in particular, this will start a new activity
        fab_add.setOnClickListener { _ ->
            val intent = Intent(this, NotaCrud::class.java)
            startActivity(intent)
        }

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
        val recyclerView = nota_list
        val adapter = NotaAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        /* We need the data to populate the list with. For this we will retrieve the ViewModel
           that we have defined, which in our case is a `DataRecordViewModel::class.java`, from
           the ViewModelProvider service.
        */
        notaViewModel = ViewModelProvider(this).get(NotaViewModel::class.java)

        /* Simply associate an observer with each of the items contained in the viewmodel.

           Notice that this can be done because `allItems` in the `DataRecordViewModel` is a `LiveData`
           object.

           The method `setItems` of the `DataRecordAdapter` class, takes care of populating each
           line of the RecyclerView, according to the layout specified in `datarecord_viewholder`.

           This is also where we set any actions (click, longclick...) per item in the list.

        */
        notaViewModel.allItems.observe(this, Observer { items ->
            items?.let { adapter.setItems(it) }
        })

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()

        val message = intent.getStringExtra(MESSAGE_MAPS)

        if (message == "maps") {

            val intent = Intent(this, MapsActivity::class.java)

            startActivity(intent)

        }
        else {

            val intent = Intent(this, MainActivity::class.java)

            startActivity(intent)

        }

        return true
    }

}
