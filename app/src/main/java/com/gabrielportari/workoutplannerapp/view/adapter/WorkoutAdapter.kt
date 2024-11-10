package com.gabrielportari.workoutplannerapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gabrielportari.workoutplannerapp.R
import com.gabrielportari.workoutplannerapp.databinding.WorkoutItemBinding
import com.gabrielportari.workoutplannerapp.service.listener.WorkoutListener
import com.gabrielportari.workoutplannerapp.service.model.Workout
import com.gabrielportari.workoutplannerapp.view.viewholder.WorkoutViewHolder
import com.google.android.material.snackbar.Snackbar

class WorkoutAdapter() : RecyclerView.Adapter<WorkoutViewHolder>() {

    private var workouts: List<Workout> = arrayListOf()
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

    fun updateWorkouts(list: List<Workout>){
        workouts = list
        notifyDataSetChanged()
    }
}