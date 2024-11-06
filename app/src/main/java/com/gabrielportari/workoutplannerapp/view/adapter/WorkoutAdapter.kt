package com.gabrielportari.workoutplannerapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gabrielportari.workoutplannerapp.R
import com.gabrielportari.workoutplannerapp.service.model.Workout
import com.gabrielportari.workoutplannerapp.view.viewholder.WorkoutViewHolder

class WorkoutAdapter(
    private val workouts: List<Workout>
    //private val onItemClickListener: (Workout) -> Unit
) : RecyclerView.Adapter<WorkoutViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.workout_item, parent, false)
        return WorkoutViewHolder(view)
    }

    override fun getItemCount(): Int {
        return workouts.size
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        if(workouts[position].NEW_CONTROLER){
            holder.itemView.findViewById<TextView>(R.id.text_workout_name).visibility = View.GONE
            holder.itemView.findViewById<TextView>(R.id.text_workout_description).visibility = View.GONE
            holder.itemView.findViewById<ImageView>(R.id.image_new_workout).visibility = View.VISIBLE
        }else {
            val workout = workouts[position]
            holder.bind(workout)
            //holder.itemView.setOnClickListener { onItemClickListener(workout) }
        }
    }

}