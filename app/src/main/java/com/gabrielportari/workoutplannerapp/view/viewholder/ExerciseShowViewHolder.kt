package com.gabrielportari.workoutplannerapp.view.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.gabrielportari.workoutplannerapp.data.constants.MyConstants
import com.gabrielportari.workoutplannerapp.data.model.Exercise
import com.gabrielportari.workoutplannerapp.databinding.ExerciseShowItemBinding

class ExerciseShowViewHolder(private val itemBinding: ExerciseShowItemBinding) : RecyclerView.ViewHolder(itemBinding.root){

    fun bind(exercise: Exercise){

        if(exercise.controller == MyConstants.CONTROLLER.CONTROLLER_TRUE) {
            itemBinding.layoutExerciseShow.visibility = View.GONE
        }else{
            itemBinding.textShowExerciseName.text = exercise.name
            if(exercise.description == ""){
                itemBinding.textShowExerciseDescription.visibility = View.GONE
            }else{
                itemBinding.textShowExerciseDescription.visibility = View.VISIBLE
                itemBinding.textShowExerciseDescription.text = exercise.description
            }
            itemBinding.textShowExerciseReps.text = exercise.repCount
        }
    }
}