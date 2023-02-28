package com.ape.meditationretreattimer.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ape.meditationretreattimer.R
import com.ape.meditationretreattimer.model.Timer

class TimerListItemAdapter(
    private val context: Context,
    private val timers: List<Timer>,
    private val itemClickListener: OnTimerListItemClickListener
) : ListAdapter<Timer, TimerListItemAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.timer_name)
        private val startButton: View = view.findViewById(R.id.start_button)
        private val editButton: View = view.findViewById(R.id.edit_button)
        private val deleteButton: View = view.findViewById(R.id.delete_button)

        fun bind(timer: Timer, clickListener: OnTimerListItemClickListener) {
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

    companion object DiffCallback : DiffUtil.ItemCallback<Timer>() {
        override fun areItemsTheSame(oldItem: Timer, newItem: Timer): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Timer, newItem: Timer): Boolean {
            return oldItem == newItem
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

interface OnTimerListItemClickListener {
    fun onStartClick(timer: Timer)
    fun onEditClick(timer: Timer)
    fun onDeleteClick(timer: Timer)
}