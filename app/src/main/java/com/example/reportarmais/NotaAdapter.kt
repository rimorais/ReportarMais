package com.example.reportarmais

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.reportarmais.Constantes
import com.example.reportarmais.NotaCrud
import com.example.reportarmais.R
import com.example.reportarmais.Nota

private const val TAG = "NotaAdapter"

class NotaAdapter internal constructor(context: Context) :

    RecyclerView.Adapter<NotaAdapter.NotaViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var itemsList = emptyList<Nota>().toMutableList()

    private val onClickListener: View.OnClickListener

    init {
        /*
        * Colocar um onClickLister em todos os objetos
        *  */
        onClickListener = View.OnClickListener { v ->
            val item = v.tag as Nota

            Log.d(TAG, "Definir onClickListener para o item ${item.id}")

            /*
               Começar uma nova intent com Extra Data
             */

            val intent = Intent(v.context, NotaCrud::class.java).apply {
                putExtra(Constantes.NOTA_ID, item.id)
            }
            v.context.startActivity(intent)
        }
    }

    /*
    É uma classe que associa os items no ViewHolder
       layout com as variáveis que vão ser usadas dentro do OnBindViewHolder
    */
    inner class NotaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemId: TextView = itemView.findViewById(R.id.nota_viewholder_id)
        val itemRecord: TextView = itemView.findViewById(R.id.nota_viewholder_record)
    }

    /* Faz o inflate da ViewHolder layout e retorna o objeto ViewHolder
    */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotaViewHolder {
        val itemView = inflater.inflate(R.layout.nota_viewholder, parent, false)
        return NotaViewHolder(itemView)
    }

    /*
       É aqui que o ViewHolder é populado com os dados do Item
     */
    override fun onBindViewHolder(holder: NotaViewHolder, position: Int) {
        val current = itemsList[position]

        // Necessário: vai ser referenciado no View.OnClickListener em cima
        holder.itemView.tag = current

        with(holder) {
            // Definir valores da UI
            itemId.text = current.id.toString()
            itemRecord.text = current.texto

            // Definir handlers
            itemView.setOnClickListener(onClickListener)
        }
    }

    internal fun setItems(items: List<Nota>) {
        this.itemsList = items.toMutableList()
        notifyDataSetChanged()
    }

    override fun getItemCount() = itemsList.size
}