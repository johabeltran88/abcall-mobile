package com.example.test.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.model.Notification
import com.example.test.model.Pcc

class NotificationAdapter(var listNotifications: List<Notification>) : RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {
    // ViewHolder class that holds references to the UI components for each list item
    class NotificationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dateTextView: TextView = view.findViewById(R.id.pcc_date)
        val statusTextView: TextView = view.findViewById(R.id.pcc_status)
        val descriptionTextView: TextView = view.findViewById(R.id.pcc_description)
    }

    // Inflates the item layout and returns a ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_notification, parent, false)
        return NotificationViewHolder(view)
    }

    // Binds data to the views in the ViewHolder
    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val notification = listNotifications[position]
        holder.itemView.setOnClickListener {
            onItemClick(position)
        }
        holder.dateTextView.text = notification.date ?: "Unknown"
        holder.statusTextView.text = notification.status ?: "Unknown"
        holder.descriptionTextView.text = notification.reason ?: "Unknown"
    }

    private var onItemClickListener: ((Int) -> Unit)? = null

    private fun onItemClick(position: Int) {
        onItemClickListener?.invoke(position)
    }

    fun updateNotification(newListNotification: List<Notification>) {
        val diffResult = DiffUtil.calculateDiff(DiffCallback(listNotifications, newListNotification))
        listNotifications = newListNotification
        diffResult.dispatchUpdatesTo(this)
    }

    // Returns the size of the dataset
    override fun getItemCount() = listNotifications.size

    private class DiffCallback(
        private val oldAlbums: List<Notification>,
        private val newAlbums: List<Notification>
    ) : DiffUtil.Callback() {
        override fun getOldListSize() = oldAlbums.size
        override fun getNewListSize() = newAlbums.size
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldAlbums[oldItemPosition].date == newAlbums[newItemPosition].date
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldAlbums[oldItemPosition] == newAlbums[newItemPosition]
        }
    }
}