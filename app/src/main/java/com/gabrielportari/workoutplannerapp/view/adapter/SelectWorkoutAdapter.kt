package com.gabrielportari.workoutplannerapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gabrielportari.workoutplannerapp.data.listener.SelectWorkoutListener
import com.gabrielportari.workoutplannerapp.data.model.Workout
import com.gabrielportari.workoutplannerapp.databinding.SelectWorkoutItemBinding
import com.gabrielportari.workoutplannerapp.view.viewholder.SelectWorkoutViewHolder

class SelectWorkoutAdapter : RecyclerView.Adapter<SelectWorkoutViewHolder>(){
    private var workouts: List<Workout> = listOf()
    private lateinit var listener: SelectWorkoutListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectWorkoutViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = SelectWorkoutItemBinding.inflate(inflater, parent, false)
        return SelectWorkoutViewHolder(itemBinding, listener)

    }

    override fun getItemCount(): Int {
        return workouts.size
    }

    override fun onBindViewHolder(holder: SelectWorkoutViewHolder, position: Int) {
        val workout = workouts[position]
        holder.bind(workout)
    }

    fun attachListener(workoutListener: SelectWorkoutListener) {
        listener = workoutListener
    }

    fun updateWorkouts(list: List<Workout>){
        workouts = list
        notifyDataSetChanged()
    }


}