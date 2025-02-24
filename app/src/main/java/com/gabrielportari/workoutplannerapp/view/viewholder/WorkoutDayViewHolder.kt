package com.gabrielportari.workoutplannerapp.view.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.gabrielportari.workoutplannerapp.data.constants.MyConstants
import com.gabrielportari.workoutplannerapp.data.listener.WorkoutDayListener
import com.gabrielportari.workoutplannerapp.data.listener.WorkoutListener
import com.gabrielportari.workoutplannerapp.data.model.WorkoutDay
import com.gabrielportari.workoutplannerapp.databinding.WorkoutDayItemBinding

class WorkoutDayViewHolder(private val itemBinding: WorkoutDayItemBinding, val listener: WorkoutDayListener) :
RecyclerView.ViewHolder(itemBinding.root){

    fun bind(workoutDay: WorkoutDay){

        if(workoutDay.workout?.controller != MyConstants.CONTROLLER.CONTROLLER_TRUE){

            itemBinding.imageNewWorkoutDay.visibility = View.GONE
            itemBinding.layoutWorkoutDayItem.visibility = View.VISIBLE

            itemBinding.textWorkoutDayWeekDay.text = workoutDay.day
            itemBinding.textWorkoutDayName.text = workoutDay.workout?.name
            itemBinding.textWorkoutDayDescription.text = workoutDay.workout?.description

        }else{
            itemBinding.textWorkoutDayWeekDay.text = workoutDay.day
            itemBinding.imageNewWorkoutDay.visibility = View.VISIBLE
            itemBinding.layoutWorkoutDayItem.visibility = View.GONE
        }

        itemBinding.imageNewWorkoutDay.setOnClickListener {
            listener.onNewClick(workoutDay.key)
        }

        itemBinding.imageDeleteWorkoutDay.setOnClickListener {
            listener.onDeleteClick(workoutDay.key, workoutDay.workout!!.id)
        }


    }
}