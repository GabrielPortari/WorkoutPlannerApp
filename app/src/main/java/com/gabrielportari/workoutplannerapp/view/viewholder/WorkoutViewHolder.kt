package com.gabrielportari.workoutplannerapp.view.viewholder

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.gabrielportari.workoutplannerapp.databinding.WorkoutItemBinding
import com.gabrielportari.workoutplannerapp.data.listener.WorkoutListener
import com.gabrielportari.workoutplannerapp.data.model.Workout
import com.gabrielportari.workoutplannerapp.view.activity.NewWorkoutActivity
import com.google.android.material.snackbar.Snackbar

class WorkoutViewHolder(private val itemBinding: WorkoutItemBinding,val listener: WorkoutListener) :
    RecyclerView.ViewHolder(itemBinding.root) {
    fun bind(workout: Workout) {

        /* Mostra conteudo dos treinos, e atribui o ultimo item como botão de adicionar novo */
        if(!workout.NEW_CONTROLER){
            itemBinding.textWorkoutName.text = workout.name
            itemBinding.textWorkoutDescription.text = workout.description
        }else{
            itemBinding.imageNewWorkout.visibility = View.VISIBLE
            itemBinding.layoutWorkoutItem.visibility = View.GONE
        }

        /* Eventos */
        itemBinding.imageDeleteWorkout.setOnClickListener {
            listener.onDeleteClick(workout.idWorkout)
        }

        itemBinding.layoutWorkoutEdit.setOnClickListener {
            listener.onEditClick(workout.idWorkout)
        }

        itemBinding.imageNewWorkout.setOnClickListener {
            listener.onNewClick()
        }
    }
}