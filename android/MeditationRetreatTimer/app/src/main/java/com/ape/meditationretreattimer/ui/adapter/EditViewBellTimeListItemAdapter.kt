package com.ape.meditationretreattimer.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ape.meditationretreattimer.R
import com.ape.meditationretreattimer.Utils
import com.ape.meditationretreattimer.model.Segment
import com.ape.meditationretreattimer.model.Timer

class EditViewBellTimeListItemAdapter(
    private val context: Context,
    private val segments: List<Segment>,
    private val itemClickListener: OnEditBellTimeItemClickListener)
    : RecyclerView.Adapter<EditViewBellTimeListItemAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val timeTextView: TextView = view.findViewById(R.id.bell_time)
        val nameTextView: TextView = view.findViewById(R.id.bell_time_name)
        private val editButton: View = view.findViewById(R.id.edit_button)
        private val deleteButton: View = view.findViewById(R.id.delete_button)

        fun bind(segment: Segment, clickListener: OnEditBellTimeItemClickListener) {
            editButton.setOnClickListener {
                clickListener.onEditClick(segment)
            }

            deleteButton.setOnClickListener {
                clickListener.onDeleteClick(segment)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.bell_times_edit_row_item, parent, false)

        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val segment = segments[position]
        viewHolder.timeTextView.text =
            "${Utils.formatLocalTime(segment.startTime)}–${Utils.formatLocalTime(segment.endTime)}"
        viewHolder.nameTextView.text = segment.name
        viewHolder.bind(segments[position], itemClickListener)
    }

    override fun getItemCount() = segments.size
}

interface OnEditBellTimeItemClickListener {
    fun onEditClick(segment: Segment)
    fun onDeleteClick(segment: Segment)
}
