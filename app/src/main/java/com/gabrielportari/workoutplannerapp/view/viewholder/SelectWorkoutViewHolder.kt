package com.gabrielportari.workoutplannerapp.view.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.gabrielportari.workoutplannerapp.data.constants.MyConstants
import com.gabrielportari.workoutplannerapp.data.listener.SelectWorkoutListener
import com.gabrielportari.workoutplannerapp.data.model.Workout
import com.gabrielportari.workoutplannerapp.databinding.SelectWorkoutItemBinding

class SelectWorkoutViewHolder(private val itemBinding: SelectWorkoutItemBinding, val listener: SelectWorkoutListener) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(workout: Workout) {
        if(workout.controller != MyConstants.CONTROLLER.CONTROLLER_TRUE) {
            itemBinding.textSelectName.text = workout.name
            itemBinding.textSelectDescription.text = workout.description

            itemBinding.layoutSelectItem.setOnClickListener {
                listener.onSelect(workout.id)
            }
        }
    }
}