package com.gabrielportari.workoutplannerapp.view.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gabrielportari.workoutplannerapp.data.constants.MyConstants
import com.gabrielportari.workoutplannerapp.databinding.FragmentManageWorkoutBinding
import com.gabrielportari.workoutplannerapp.data.listener.WorkoutListener
import com.gabrielportari.workoutplannerapp.data.model.Workout
import com.gabrielportari.workoutplannerapp.view.activity.NewWorkoutFormActivity
import com.gabrielportari.workoutplannerapp.view.adapter.WorkoutAdapter
import com.gabrielportari.workoutplannerapp.viewmodel.ManageWorkoutViewModel


class ManageWorkoutFragment : Fragment() {

    private lateinit var viewModel: ManageWorkoutViewModel
    private var _binding: FragmentManageWorkoutBinding? = null
    private val binding get() = _binding!!

    private var workoutId = 0
    private val adapter = WorkoutAdapter()
    private var workouts = listOf<Workout>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(ManageWorkoutViewModel::class.java)
        _binding = FragmentManageWorkoutBinding.inflate(inflater, container, false)

        binding.recyclerWorkouts.layoutManager = LinearLayoutManager(context)
        binding.recyclerWorkouts.adapter = adapter
        adapter.updateWorkouts(workouts)

        val listener = object: WorkoutListener {
            override fun onNewClick() {
                val intent = Intent(context, NewWorkoutFormActivity::class.java)
                startActivity(intent)
            }

            override fun onDeleteClick(id: Int) {
                val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(context)
                dialogBuilder.setTitle("Deletar Treino")
                dialogBuilder.setMessage("Tem certeza que deseja excluir esse treino?")
                dialogBuilder.setPositiveButton("Sim") { _, _ ->
                    viewModel.deleteWorkout(id)
                }
                dialogBuilder.setNegativeButton("Nao") { _, _ ->

                }
                val dialog = dialogBuilder.create()
                dialog.show()
            }

            override fun onEditClick(workout: Workout) {
                val intent = Intent(context, NewWorkoutFormActivity::class.java)
                val bundle = Bundle()
                bundle.putInt(MyConstants.KEY.ID_KEY, workout.id)
                intent.putExtras(bundle)
                startActivity(intent)
            }
        }

        adapter.attachListener(listener)


        observe()

        return binding.root
    }

    private fun observe(){
        viewModel.workoutList.observe(viewLifecycleOwner){
            adapter.updateWorkouts(it)
        }

        viewModel.validation.observe(viewLifecycleOwner){
            if(it.status()){
                if(workoutId == 0){
                    showToast("Treino criado com sucesso")
                }else{
                    showToast("Treino editado com sucesso")
                }
            }else{
                showToast(it.message())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        viewModel.listWorkouts()
    }

    private fun showToast(message: String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}