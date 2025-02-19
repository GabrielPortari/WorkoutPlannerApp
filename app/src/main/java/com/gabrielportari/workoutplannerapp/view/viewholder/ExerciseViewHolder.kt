package com.gabrielportari.workoutplannerapp.view.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.gabrielportari.workoutplannerapp.data.constants.MyConstants
import com.gabrielportari.workoutplannerapp.data.listener.ExerciseListener
import com.gabrielportari.workoutplannerapp.data.model.Exercise
import com.gabrielportari.workoutplannerapp.databinding.ExerciseItemBinding

class ExerciseViewHolder(private val itemBinding: ExerciseItemBinding, val listener: ExerciseListener) :
    RecyclerView.ViewHolder(itemBinding.root){

    fun bind(exercise: Exercise){

        /*Mostra o conteudo dos exercicios, caso possua controller = true, mostra o botão de adicionar novo */
        if(exercise.controller != MyConstants.CONTROLLER.CONTROLLER_TRUE) {
            itemBinding.imageNewExercise.visibility = View.GONE
            itemBinding.layoutExerciseItem.visibility = View.VISIBLE

            itemBinding.textExerciseName.text = exercise.name
            if(exercise.description == ""){
                itemBinding.textExerciseDescription.visibility = View.GONE
            }else{
                itemBinding.textExerciseDescription.visibility = View.VISIBLE
                itemBinding.textExerciseDescription.text = exercise.description
            }
            itemBinding.textExerciseReps.text = exercise.repCount
        }else{
            itemBinding.imageNewExercise.visibility = View.VISIBLE
            itemBinding.layoutExerciseItem.visibility = View.GONE
        }

        /* Eventos */
        itemBinding.imageDeleteExercise.setOnClickListener {
            listener.onDeleteClick(exercise.id)
        }
        itemBinding.layoutExerciseEdit.setOnClickListener {
            listener.onEditClick(exercise)
        }
        itemBinding.imageNewExercise.setOnClickListener {
            listener.onNewClick()
        }
    }
}