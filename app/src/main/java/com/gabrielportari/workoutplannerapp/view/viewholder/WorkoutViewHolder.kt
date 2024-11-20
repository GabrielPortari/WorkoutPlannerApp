package com.gabrielportari.workoutplannerapp.view.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.gabrielportari.workoutplannerapp.data.constants.MyConstants
import com.gabrielportari.workoutplannerapp.databinding.WorkoutItemBinding
import com.gabrielportari.workoutplannerapp.data.listener.WorkoutListener
import com.gabrielportari.workoutplannerapp.data.model.Workout

class WorkoutViewHolder(private val itemBinding: WorkoutItemBinding,val listener: WorkoutListener) :
    RecyclerView.ViewHolder(itemBinding.root) {
    fun bind(workout: Workout) {

        /* Mostra conteudo dos treinos, e atribui o ultimo item como botão de adicionar novo */
        if(workout.controller != MyConstants.CONTROLLER.CONTROLLER_TRUE){
            itemBinding.textWorkoutName.text = workout.name
            itemBinding.textWorkoutDescription.text = workout.description
        }else{
            itemBinding.imageNewWorkout.visibility = View.VISIBLE
            itemBinding.layoutWorkoutItem.visibility = View.GONE
        }

        /* Eventos */
        itemBinding.imageDeleteWorkout.setOnClickListener {
            listener.onDeleteClick(workout.id)
        }

        itemBinding.layoutWorkoutEdit.setOnClickListener {
            listener.onEditClick(workout)
        }

        itemBinding.imageNewWorkout.setOnClickListener {
            listener.onNewClick()
        }
    }
}