package com.example.reportarmais.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.reportarmais.R
import com.example.reportarmais.entities.Nota

class NotaAdapter : ListAdapter<Nota, NotaAdapter.NotaViewHolder>(NotaComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotaViewHolder {
        return NotaViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: NotaViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.titulo)
    }

    class NotaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val wordItemView: TextView = itemView.findViewById(R.id.textView)

        fun bind(text: String?) {
            wordItemView.text = text
        }

        companion object {
            fun create(parent: ViewGroup): NotaViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return NotaViewHolder(view)
            }
        }
    }

    class NotaComparator : DiffUtil.ItemCallback<Nota>() {
        override fun areItemsTheSame(oldItem: Nota, newItem: Nota): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Nota, newItem: Nota): Boolean {
            return oldItem.titulo == newItem.titulo
        }
    }
}