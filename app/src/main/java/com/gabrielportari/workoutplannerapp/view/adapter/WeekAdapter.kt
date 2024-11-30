package com.gabrielportari.workoutplannerapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gabrielportari.workoutplannerapp.data.listener.WeekListener
import com.gabrielportari.workoutplannerapp.data.model.Week
import com.gabrielportari.workoutplannerapp.databinding.WeekItemBinding
import com.gabrielportari.workoutplannerapp.view.viewholder.WeekViewHolder

class WeekAdapter() : RecyclerView.Adapter<WeekViewHolder>() {

    private var weeks: List<Week> = listOf()
    private lateinit var listener: WeekListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = WeekItemBinding.inflate(inflater, parent, false)
        return WeekViewHolder(itemBinding, listener)
    }

    override fun getItemCount(): Int {
        return weeks.size
    }

    override fun onBindViewHolder(holder: WeekViewHolder, position: Int) {
        val week = weeks[position]
        holder.bind(week)
    }

    fun attachListener(weekListener: WeekListener) {
        listener = weekListener
    }

    fun updateWeeks(list: List<Week>){
        weeks = list
        notifyDataSetChanged()
    }
    
}