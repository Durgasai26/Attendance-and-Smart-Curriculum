package com.example.attendencescanner.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.attendencescanner.R
import com.example.attendencescanner.data.model.AttendanceWithUser

class AttendanceAdapter : RecyclerView.Adapter<AttendanceAdapter.VH>() {
    private val items = mutableListOf<AttendanceWithUser>()
    var onEdit: ((AttendanceWithUser) -> Unit)? = null
    var onDelete: ((AttendanceWithUser) -> Unit)? = null

    fun submit(list: List<AttendanceWithUser>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_attendance, parent, false)
        return VH(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { onEdit?.invoke(item) }
        holder.itemView.setOnLongClickListener { onDelete?.invoke(item); true }
    }

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.txtClass)
        private val subtitle: TextView = itemView.findViewById(R.id.txtDateTime)
        fun bind(item: AttendanceWithUser) {
            title.text = item.studentName
            subtitle.text = "${item.className} • ${item.date} ${item.time}"
        }
    }
}


