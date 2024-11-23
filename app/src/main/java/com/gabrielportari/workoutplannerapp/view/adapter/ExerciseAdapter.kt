package com.gabrielportari.workoutplannerapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gabrielportari.workoutplannerapp.data.listener.ExerciseListener
import com.gabrielportari.workoutplannerapp.data.model.Exercise
import com.gabrielportari.workoutplannerapp.databinding.ExerciseItemBinding
import com.gabrielportari.workoutplannerapp.view.viewholder.ExerciseViewHolder

class ExerciseAdapter() : RecyclerView.Adapter<ExerciseViewHolder>() {

    private var exercises: List<Exercise> = listOf()
    private lateinit var listener: ExerciseListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = ExerciseItemBinding.inflate(inflater, parent, false)
        return ExerciseViewHolder(itemBinding, listener)
    }

    override fun getItemCount(): Int {
        return exercises.size
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = exercises[position]
        holder.bind(exercise)
    }

    fun attachListener(exerciseListener: ExerciseListener) {
        listener = exerciseListener
    }

    fun updateExercises(list: List<Exercise>){
        exercises = list
        notifyDataSetChanged()
    }


}