package com.github.magervasoni.marcelagervasoni_rm94668

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemsAdapter(private val onItemRemoved: (ItemModel) -> Unit) :
    RecyclerView.Adapter<ItemsAdapter.ItemViewHolder>() {

    private var items = listOf<ItemModel>()

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tituloTextView = view.findViewById<TextView>(R.id.tituloTextView)
        private val descricaoTextView = view.findViewById<TextView>(R.id.descricaoTextView)
        private val button = view.findViewById<ImageButton>(R.id.imageButton)

        fun bind(item: ItemModel) {
            tituloTextView.text = item.titulo
            descricaoTextView.text = item.descricao

            button.setOnClickListener {
                onItemRemoved(item)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_dica, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }
    fun updateItems(newItems: List<ItemModel>) {
        items = newItems
        notifyDataSetChanged()
    }
}
