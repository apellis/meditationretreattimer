package com.ape.meditationretreattimer.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ape.meditationretreattimer.R
import com.ape.meditationretreattimer.model.Segment

class BellTimeListItemAdapter(
    private val context: Context,
    private val segments: Array<Segment>)
    : RecyclerView.Adapter<BellTimeListItemAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val timeTextView: TextView = view.findViewById(R.id.bell_time)
        val nameTextView: TextView = view.findViewById(R.id.bell_time_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.bell_times_row_item, parent, false)

        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.timeTextView.text = "${segments[position].startTime}–${segments[position].endTime}"
        viewHolder.nameTextView.text = segments[position].name
    }

    override fun getItemCount() = segments.size
}
