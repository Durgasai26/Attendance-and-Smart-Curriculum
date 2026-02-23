package com.example.attendencescanner.ui.history


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.attendencescanner.databinding.ItemActivityBinding
import com.example.attendencescanner.data.entities.CurriculumActivity

class ActivityAdapter(private val onClick: (CurriculumActivity) -> Unit)
    : RecyclerView.Adapter<ActivityAdapter.ActivityViewHolder>() {

    private var items = listOf<CurriculumActivity>()

    inner class ActivityViewHolder(val binding: ItemActivityBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        val binding = ItemActivityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ActivityViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        val item = items[position]
        holder.binding.tvTitle.text = item.title
        holder.binding.tvSubject.text = item.subject
        holder.binding.tvStatus.text = item.status
        holder.itemView.setOnClickListener { onClick(item) }
    }

    fun submitList(list: List<CurriculumActivity>) {
        items = list
        notifyDataSetChanged()
    }
}
