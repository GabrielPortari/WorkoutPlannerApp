package com.gabrielportari.workoutplannerapp.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.gabrielportari.workoutplannerapp.R
import com.gabrielportari.workoutplannerapp.databinding.FragmentManageWorkoutBinding
import com.gabrielportari.workoutplannerapp.service.helper.EmptyDataObserver
import com.gabrielportari.workoutplannerapp.service.model.Workout
import com.gabrielportari.workoutplannerapp.view.adapter.WorkoutAdapter


class ManageWorkoutFragment : Fragment() {

    private var _binding: FragmentManageWorkoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentManageWorkoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val workout = listOf(generateRandomWorkouts(), Workout("", "", emptyList(), true))
        //val workout = emptyList<Workout>()

        binding.recyclerWorkouts.layoutManager = LinearLayoutManager(context)
        val adapter = WorkoutAdapter(workout)
        binding.recyclerWorkouts.adapter = adapter

        val emptyDataObserver = EmptyDataObserver(binding.recyclerWorkouts, view.findViewById(R.id.empty_data_parent))
        adapter.registerAdapterDataObserver(emptyDataObserver)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun generateRandomWorkouts(): Workout {
        val workoutNames = listOf("Treino A", "Treino B", "Treino C", "Treino D", "Treino E")
        val workoutDescriptions = listOf("Treino focado em peitoral, tríceps e ombro",
            "Treino focado em costas, bíceps e antebraços", "Treino de perna completo, incluindo posterior, quadriceps e panturrilha",
            "Treino de braço, com foco em bíceps", "Treino completo de ombro, com estímulo peito")

        val randomName = workoutNames.random()
        val randomDescription = workoutDescriptions.random()

        return Workout(randomName, randomDescription, emptyList())

    }
}