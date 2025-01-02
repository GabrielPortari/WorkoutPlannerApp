package com.gabrielportari.workoutplannerapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gabrielportari.workoutplannerapp.data.listener.ExerciseListener
import com.gabrielportari.workoutplannerapp.data.model.Exercise
import com.gabrielportari.workoutplannerapp.databinding.ExerciseShowItemBinding
import com.gabrielportari.workoutplannerapp.view.viewholder.ExerciseShowViewHolder
import com.gabrielportari.workoutplannerapp.view.viewholder.ExerciseViewHolder

class ExerciseShowAdapter() : RecyclerView.Adapter<ExerciseShowViewHolder>() {

    private var exercises: List<Exercise> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseShowViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = ExerciseShowItemBinding.inflate(inflater, parent, false)
        return ExerciseShowViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return exercises.size
    }

    override fun onBindViewHolder(holder: ExerciseShowViewHolder, position: Int) {
        val exercise = exercises[position]
        holder.bind(exercise)
    }

    fun updateExercises(list: List<Exercise>){
        exercises = list
        notifyDataSetChanged()
    }


}