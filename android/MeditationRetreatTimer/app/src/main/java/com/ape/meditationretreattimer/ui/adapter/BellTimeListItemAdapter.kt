package com.ape.meditationretreattimer.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ape.meditationretreattimer.R
import com.ape.meditationretreattimer.Utils
import com.ape.meditationretreattimer.model.Segment

class BellTimeListItemAdapter(
    private val context: Context,
    private val segments: List<Segment>)
    : RecyclerView.Adapter<BellTimeListItemAdapter.ViewHolder>() {

    private var selectedPos = RecyclerView.NO_POSITION

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
        val segment = segments[position]
        viewHolder.timeTextView.text =
            "${Utils.formatLocalTime(segment.startTime)}–${Utils.formatLocalTime(segment.endTime)}"
        viewHolder.nameTextView.text = segment.name
        viewHolder.timeTextView.setTypeface(
            null, if (position == selectedPos) Typeface.BOLD else Typeface.NORMAL)
        viewHolder.nameTextView.setTypeface(
            null, if (position == selectedPos) Typeface.BOLD else Typeface.NORMAL)
    }

    override fun getItemCount() = segments.size

    fun setSelectedPos(position: Int, onChangeCallback: (() -> Unit)?) {
        val oldSelectedPos = selectedPos
        selectedPos = position
        notifyItemChanged(oldSelectedPos)
        notifyItemChanged(selectedPos)

        if (selectedPos != oldSelectedPos && onChangeCallback != null) {
            onChangeCallback()
        }
    }
}
