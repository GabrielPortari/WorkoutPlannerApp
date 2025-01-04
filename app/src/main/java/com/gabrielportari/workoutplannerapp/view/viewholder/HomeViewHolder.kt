package com.gabrielportari.workoutplannerapp.view.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.gabrielportari.workoutplannerapp.R
import com.gabrielportari.workoutplannerapp.data.constants.MyConstants
import com.gabrielportari.workoutplannerapp.data.listener.SelectWorkoutListener
import com.gabrielportari.workoutplannerapp.data.model.WorkoutDay
import com.gabrielportari.workoutplannerapp.databinding.WorkoutOfDayItemBinding


class HomeViewHolder(private val itemBinding: WorkoutOfDayItemBinding, val listener: SelectWorkoutListener):
RecyclerView.ViewHolder(itemBinding.root){

    private val resources = itemBinding.root.resources

    fun bind(workout: WorkoutDay){
        if(workout.workout?.controller == MyConstants.CONTROLLER.CONTROLLER_TRUE){
            itemBinding.textDayOfWeek.text = workout.day
            itemBinding.textWorkoutOfDayName.text = resources.getString(R.string.rest_day)
            itemBinding.textWorkoutOfDayDescription.text = resources.getString(R.string.rest_day_description)
        }else {
            itemBinding.textDayOfWeek.text = workout.day
            itemBinding.textWorkoutOfDayName.text = workout.workout?.name
            itemBinding.textWorkoutOfDayDescription.text = workout.workout?.description

            itemBinding.layoutWorkoutDayItem.setOnClickListener {
                listener.onSelect(workout.workout!!.id)
            }
        }
    }

}