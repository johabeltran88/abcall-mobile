package com.example.test.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.model.Pcc

class PccAdapter(var listPcc: List<Pcc>) : RecyclerView.Adapter<PccAdapter.PccViewHolder>() {
    // ViewHolder class that holds references to the UI components for each list item
    class PccViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idTextView: TextView = view.findViewById(R.id.pcc_id)
        val nameTextView: TextView = view.findViewById(R.id.pcc_subject)
    }

    // Inflates the item layout and returns a ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PccViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pcc, parent, false)
        return PccViewHolder(view)
    }

    // Binds data to the views in the ViewHolder
    override fun onBindViewHolder(holder: PccViewHolder, position: Int) {
        val pcc = listPcc[position]
        holder.itemView.setOnClickListener {
            onItemClick(position)
        }
        holder.idTextView.text = pcc.id ?: "Unknown"
        holder.nameTextView.text = pcc.subject ?: "Unknown"
    }

    private var onItemClickListener: ((Int) -> Unit)? = null

    private fun onItemClick(position: Int) {
        onItemClickListener?.invoke(position)
    }

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }

    fun updatePcc(newListPcc: List<Pcc>) {
        val diffResult = DiffUtil.calculateDiff(DiffCallback(listPcc, newListPcc))
        listPcc = newListPcc
        diffResult.dispatchUpdatesTo(this)
    }

    // Returns the size of the dataset
    override fun getItemCount() = listPcc.size

    private class DiffCallback(
        private val oldAlbums: List<Pcc>,
        private val newAlbums: List<Pcc>
    ) : DiffUtil.Callback() {
        override fun getOldListSize() = oldAlbums.size
        override fun getNewListSize() = newAlbums.size
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldAlbums[oldItemPosition].id == newAlbums[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldAlbums[oldItemPosition] == newAlbums[newItemPosition]
        }
    }
}