package com.gabrielportari.workoutplannerapp.view.viewholder

import android.view.Gravity
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.gabrielportari.workoutplannerapp.data.constants.MyConstants
import com.gabrielportari.workoutplannerapp.data.listener.SelectWorkoutListener
import com.gabrielportari.workoutplannerapp.data.model.Workout
import com.gabrielportari.workoutplannerapp.data.model.WorkoutDay
import com.gabrielportari.workoutplannerapp.databinding.WorkoutOfDayItemBinding
import java.util.Calendar

class HomeViewHolder(private val itemBinding: WorkoutOfDayItemBinding, val listener: SelectWorkoutListener):
RecyclerView.ViewHolder(itemBinding.root){

    fun bind(workout: WorkoutDay){
        if(workout.workout?.controller == MyConstants.CONTROLLER.CONTROLLER_TRUE){
            itemBinding.textDayOfWeek.text = workout.day
            itemBinding.textWorkoutOfDayName.text = "Descanso"
            itemBinding.textWorkoutOfDayDescription.text = "Dia de ficar em casa"
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