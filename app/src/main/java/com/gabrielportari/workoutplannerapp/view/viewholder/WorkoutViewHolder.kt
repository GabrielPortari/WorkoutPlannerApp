package com.gabrielportari.workoutplannerapp.view.viewholder

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.gabrielportari.workoutplannerapp.databinding.WorkoutItemBinding
import com.gabrielportari.workoutplannerapp.service.listener.WorkoutListener
import com.gabrielportari.workoutplannerapp.service.model.Workout
import com.gabrielportari.workoutplannerapp.view.activity.NewWorkoutActivity
import com.google.android.material.snackbar.Snackbar

class WorkoutViewHolder(private val itemBinding: WorkoutItemBinding,val listener: WorkoutListener) :
    RecyclerView.ViewHolder(itemBinding.root) {
    fun bind(workout: Workout) {

        /* Mostra conteudo dos treinos, e atribui o ultimo item como botão de adicionar novo */
        if(!workout.NEW_CONTROLER){
            itemBinding.textWorkoutName.text = workout.name
            itemBinding.textWorkoutDescription.text = workout.description
        }else{
            itemBinding.imageNewWorkout.visibility = View.VISIBLE
            itemBinding.layoutWorkoutItem.visibility = View.GONE
        }

        /* Eventos */
        itemBinding.imageDeleteWorkout.setOnClickListener {
            listener.onDeleteClick(workout.idWorkout)
        }

        itemBinding.layoutWorkoutEdit.setOnClickListener {
            listener.onEditClick(workout.idWorkout)
        }

        itemBinding.imageNewWorkout.setOnClickListener {
            Snackbar.make(it, "Adicionar novo treino", Snackbar.LENGTH_SHORT).show()
            val intent: Intent = Intent(it.context, NewWorkoutActivity::class.java)
            it.context.startActivity(intent)
        }
    }
}