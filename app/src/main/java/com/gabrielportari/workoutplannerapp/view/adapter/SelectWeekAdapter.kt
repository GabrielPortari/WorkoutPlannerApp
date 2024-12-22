package com.gabrielportari.workoutplannerapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gabrielportari.workoutplannerapp.data.listener.SelectWeekListener
import com.gabrielportari.workoutplannerapp.data.model.Week
import com.gabrielportari.workoutplannerapp.databinding.SelectWeekItemBinding
import com.gabrielportari.workoutplannerapp.view.viewholder.SelectWeekViewHolder

class SelectWeekAdapter: RecyclerView.Adapter<SelectWeekViewHolder>(){
    private var weeks: List<Week> = listOf()
    private lateinit var listener: SelectWeekListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectWeekViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = SelectWeekItemBinding.inflate(inflater, parent, false)
        return SelectWeekViewHolder(itemBinding, listener)

    }

    override fun getItemCount(): Int {
        return weeks.size
    }

    override fun onBindViewHolder(holder: SelectWeekViewHolder, position: Int) {
        val week = weeks[position]
        holder.bind(week)
    }

    fun attachListener(weekListener: SelectWeekListener) {
        listener = weekListener
    }

    fun updateWeeks(list: List<Week>){
        weeks = list
        notifyDataSetChanged()
    }
}