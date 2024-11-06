package com.gabrielportari.workoutplannerapp.view.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gabrielportari.workoutplannerapp.R
import com.gabrielportari.workoutplannerapp.service.model.Workout

class WorkoutViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
    fun bind(workout: Workout) {
        itemView.findViewById<TextView>(R.id.text_workout_name).text = workout.name
        itemView.findViewById<TextView>(R.id.text_workout_description).text = workout.description
    }
}