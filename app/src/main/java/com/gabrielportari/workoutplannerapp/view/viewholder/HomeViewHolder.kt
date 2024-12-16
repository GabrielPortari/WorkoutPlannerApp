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
        itemBinding.textDayOfWeek.text = workout.day
        itemBinding.textWorkoutOfDayName.text = workout.workout?.name
        itemBinding.textWorkoutOfDayDescription.text = workout.workout?.description
        if(isToday(workout.day)){
            itemBinding.textIsToday.visibility = View.VISIBLE
            itemBinding.layoutWorkoutDayItem.gravity = Gravity.TOP
        }else{
            itemBinding.textIsToday.visibility = View.GONE
            itemBinding.layoutWorkoutDayItem.gravity = Gravity.CENTER
        }

        itemBinding.layoutWorkoutDayItem.setOnClickListener{
            listener.onSelect(workout.workout!!.id)
        }
    }

    private fun isToday(day: String) : Boolean{
        return when(day){
            MyConstants.WEEK_DAYS.MONDAY -> Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY
            MyConstants.WEEK_DAYS.TUESDAY -> Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY
            MyConstants.WEEK_DAYS.WEDNESDAY -> Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY
            MyConstants.WEEK_DAYS.THURSDAY -> Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY
            MyConstants.WEEK_DAYS.FRIDAY -> Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY
            MyConstants.WEEK_DAYS.SATURDAY -> Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
            MyConstants.WEEK_DAYS.SUNDAY -> Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
            else -> false
        }
    }
}