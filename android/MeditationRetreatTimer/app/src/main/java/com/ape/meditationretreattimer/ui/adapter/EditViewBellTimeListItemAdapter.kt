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
import com.ape.meditationretreattimer.model.BellTime
import com.ape.meditationretreattimer.model.Segment
import com.ape.meditationretreattimer.model.Timer

class EditViewBellTimeListItemAdapter(
    private val context: Context,
    private val bellTimes: List<BellTime>,
    private val itemClickListener: OnEditBellTimeItemClickListener)
    : RecyclerView.Adapter<EditViewBellTimeListItemAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val timeTextView: TextView = view.findViewById(R.id.bell_time)
        val nameTextView: TextView = view.findViewById(R.id.bell_time_name)
        private val editButton: View = view.findViewById(R.id.edit_button)
        private val deleteButton: View = view.findViewById(R.id.delete_button)

        fun bind(bellTime: BellTime, clickListener: OnEditBellTimeItemClickListener) {
            editButton.setOnClickListener {
                clickListener.onEditClick(bellTime)
            }

            deleteButton.setOnClickListener {
                clickListener.onDeleteClick(bellTime)
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
        val bellTime = bellTimes[position]
        viewHolder.timeTextView.text = "${Utils.formatLocalTime(bellTime.time)}"
        viewHolder.nameTextView.text = bellTime.name
        viewHolder.bind(bellTimes[position], itemClickListener)
    }

    override fun getItemCount() = bellTimes.size
}

interface OnEditBellTimeItemClickListener {
    fun onEditClick(bellTime: BellTime)
    fun onDeleteClick(bellTime: BellTime)
}
