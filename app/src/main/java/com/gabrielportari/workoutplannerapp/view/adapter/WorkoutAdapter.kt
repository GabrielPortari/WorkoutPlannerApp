package com.gabrielportari.workoutplannerapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gabrielportari.workoutplannerapp.databinding.WorkoutItemBinding
import com.gabrielportari.workoutplannerapp.data.listener.WorkoutListener
import com.gabrielportari.workoutplannerapp.data.model.Workout
import com.gabrielportari.workoutplannerapp.view.viewholder.WorkoutViewHolder

class WorkoutAdapter() : RecyclerView.Adapter<WorkoutViewHolder>() {

    private var workouts: ArrayList<Workout> = arrayListOf()
    private lateinit var listener: WorkoutListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = WorkoutItemBinding.inflate(inflater, parent, false)
        return WorkoutViewHolder(itemBinding, listener)
    }

    override fun getItemCount(): Int {
        return workouts.size
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        val workout = workouts[position]
        holder.bind(workout)
    }

    fun attachListener(workoutListener: WorkoutListener) {
        listener = workoutListener
    }

    fun updateWorkouts(list: ArrayList<Workout>){
        workouts = list
        notifyDataSetChanged()
    }
}