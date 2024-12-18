package com.gabrielportari.workoutplannerapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gabrielportari.workoutplannerapp.data.listener.SelectWorkoutListener
import com.gabrielportari.workoutplannerapp.data.model.WorkoutDay
import com.gabrielportari.workoutplannerapp.databinding.WorkoutOfDayItemBinding
import com.gabrielportari.workoutplannerapp.view.viewholder.HomeViewHolder

class HomeAdapter : RecyclerView.Adapter<HomeViewHolder>() {
    private lateinit var listener: SelectWorkoutListener
    private var workouts: List<WorkoutDay> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = WorkoutOfDayItemBinding.inflate(inflater, parent, false)
        return HomeViewHolder(itemBinding, listener)

    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val actualPosition = position % workouts.size
        val workout = workouts[actualPosition]
        holder.bind(workout)
    }

    override fun getItemCount(): Int {
        return Integer.MAX_VALUE
    }

    fun attachListener(workoutListener: SelectWorkoutListener) {
        listener = workoutListener
    }

    fun updateWorkouts(list: List<WorkoutDay>) {
        workouts = list
        notifyDataSetChanged()
    }

}