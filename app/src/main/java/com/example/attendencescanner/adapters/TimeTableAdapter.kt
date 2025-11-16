package com.example.attendencescanner.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.attendencescanner.R
import com.example.attendencescanner.data.model.TimeTableItem


class TimeTableAdapter(private val timeTableList: List<TimeTableItem>) :
    RecyclerView.Adapter<TimeTableAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val subjectName: TextView = itemView.findViewById(R.id.textSubjectName)
        val subjectTime: TextView = itemView.findViewById(R.id.textSubjectTime)
        val subjectDay: TextView = itemView.findViewById(R.id.textSubjectDay)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_time_table, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = timeTableList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = timeTableList[position]
        holder.subjectName.text = item.subjectName
        holder.subjectTime.text = item.subjectTime
        holder.subjectDay.text = item.subjectDay
    }
}
