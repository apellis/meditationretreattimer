package com.ape.meditationretreattimer.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ape.meditationretreattimer.R
import com.ape.meditationretreattimer.model.Timer

class TimerListItemAdapter(
    private val context: Context,
    private val timers: List<Timer>,
    private val itemClickListener: OnItemClickListener)
    : RecyclerView.Adapter<TimerListItemAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.timer_name)
        private val startButton: View = view.findViewById(R.id.start_button)
        private val editButton: View = view.findViewById(R.id.edit_button)
        private val deleteButton: View = view.findViewById(R.id.delete_button)

        fun bind(timer: Timer, clickListener: OnItemClickListener) {
            startButton.setOnClickListener {
                clickListener.onStartClick(timer)
            }

            editButton.setOnClickListener {
                clickListener.onEditClick(timer)
            }

            deleteButton.setOnClickListener {
                clickListener.onDeleteClick(timer)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.timers_row_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.textView.text = timers[position].name
        viewHolder.bind(timers[position], itemClickListener)
    }

    override fun getItemCount() = timers.size
}

interface OnItemClickListener {
    fun onStartClick(timer: Timer)
    fun onEditClick(timer: Timer)
    fun onDeleteClick(timer: Timer)
}