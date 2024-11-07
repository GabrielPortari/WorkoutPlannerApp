package com.gabrielportari.workoutplannerapp.view.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gabrielportari.workoutplannerapp.R
import com.gabrielportari.workoutplannerapp.service.model.Workout
import com.google.android.material.snackbar.Snackbar

class WorkoutViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
    fun bind(workout: Workout) {

        /* Mostra conteudo dos treinos, e atribui o ultimo item como botão de adicionar novo */
        if(!workout.NEW_CONTROLER){
            itemView.findViewById<TextView>(R.id.text_workout_name).text = workout.name
            itemView.findViewById<TextView>(R.id.text_workout_description).text = workout.description
        }else{
            itemView.findViewById<ImageView>(R.id.image_new_workout).visibility = View.VISIBLE
            itemView.findViewById<LinearLayout>(R.id.layout_workout_item).visibility = View.GONE
        }

        /* Eventos */
        itemView.findViewById<ImageView>(R.id.image_delete_workout).setOnClickListener {
            Snackbar.make(it, "Deletar treino", Snackbar.LENGTH_SHORT).show()
        }

        itemView.findViewById<LinearLayout>(R.id.layout_workout_edit).setOnClickListener {
            Snackbar.make(it, "Editar treino", Snackbar.LENGTH_SHORT).show()
        }

        itemView.findViewById<ImageView>(R.id.image_new_workout).setOnClickListener {
            Snackbar.make(it, "Adicionar novo treino", Snackbar.LENGTH_SHORT).show()
        }
    }
}