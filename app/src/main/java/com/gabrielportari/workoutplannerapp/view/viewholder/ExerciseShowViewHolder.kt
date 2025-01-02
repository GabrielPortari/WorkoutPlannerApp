package com.gabrielportari.workoutplannerapp.view.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.gabrielportari.workoutplannerapp.data.listener.ExerciseListener
import com.gabrielportari.workoutplannerapp.data.model.Exercise
import com.gabrielportari.workoutplannerapp.databinding.ExerciseShowItemBinding

class ExerciseShowViewHolder(private val itemBinding: ExerciseShowItemBinding) : RecyclerView.ViewHolder(itemBinding.root){

    fun bind(exercise: Exercise){

        itemBinding.textShowExerciseName.text = exercise.name
        itemBinding.textShowExerciseDescription.text = exercise.description
        itemBinding.textShowExerciseReps.text = exercise.repCount

    }
}