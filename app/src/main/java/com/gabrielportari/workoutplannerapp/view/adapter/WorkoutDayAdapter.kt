package com.gabrielportari.workoutplannerapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gabrielportari.workoutplannerapp.data.listener.WorkoutDayListener
import com.gabrielportari.workoutplannerapp.data.listener.WorkoutListener
import com.gabrielportari.workoutplannerapp.data.model.WorkoutDay
import com.gabrielportari.workoutplannerapp.databinding.WorkoutDayItemBinding
import com.gabrielportari.workoutplannerapp.view.viewholder.WorkoutDayViewHolder

class WorkoutDayAdapter : RecyclerView.Adapter<WorkoutDayViewHolder>(){

    private var workoutsDay: List<WorkoutDay> = listOf()
    private lateinit var listener: WorkoutDayListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutDayViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = WorkoutDayItemBinding.inflate(inflater, parent, false)
        return WorkoutDayViewHolder(itemBinding, listener)

    }

    override fun onBindViewHolder(holder: WorkoutDayViewHolder, position: Int) {
        val workoutDay = workoutsDay[position]
        holder.bind(workoutDay)
    }

    override fun getItemCount(): Int {
        return workoutsDay.size
    }

    fun attachListener(workoutListener: WorkoutDayListener) {
        listener = workoutListener
    }

    fun updateWorkouts(list: List<WorkoutDay>){
        workoutsDay = list
        notifyDataSetChanged()
    }

}