package com.gabrielportari.workoutplannerapp.view.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gabrielportari.workoutplannerapp.R
import com.gabrielportari.workoutplannerapp.data.constants.MyConstants
import com.gabrielportari.workoutplannerapp.databinding.FragmentManageWorkoutBinding
import com.gabrielportari.workoutplannerapp.data.listener.WorkoutListener
import com.gabrielportari.workoutplannerapp.data.model.Week
import com.gabrielportari.workoutplannerapp.data.model.Workout
import com.gabrielportari.workoutplannerapp.view.activity.WorkoutFormActivity
import com.gabrielportari.workoutplannerapp.view.adapter.WorkoutAdapter
import com.gabrielportari.workoutplannerapp.viewmodel.ManageWorkoutViewModel


class ManageWorkoutFragment : Fragment() {

    private lateinit var viewModel: ManageWorkoutViewModel
    private var _binding: FragmentManageWorkoutBinding? = null
    private val binding get() = _binding!!

    private var workoutId = 0
    private val adapter = WorkoutAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[ManageWorkoutViewModel::class.java]
        _binding = FragmentManageWorkoutBinding.inflate(inflater, container, false)

        binding.recyclerWorkouts.layoutManager = LinearLayoutManager(context)
        binding.recyclerWorkouts.adapter = adapter

        val listener = object: WorkoutListener {
            override fun onNewClick() {
                val dialog : AlertDialog.Builder = AlertDialog.Builder(context)
                dialog.setTitle(R.string.new_workout_title)
                val view = layoutInflater.inflate(R.layout.dialog_form, null)
                dialog.setView(view)
                dialog.setPositiveButton(R.string.create) { _, _ ->
                    val name = view.findViewById<EditText>(R.id.edit_name).text.toString()
                    val description = view.findViewById<EditText>(R.id.edit_description).text.toString()
                    if(name.isBlank() || description.isBlank() ) {
                        showToast(resources.getString(R.string.fill_all_fields))
                    }
                    else {
                        val workout = Workout(workoutId,
                            name.trim().replace("\n", ""),
                            description.trim().replace("\n", ""),
                            emptyList(),
                            MyConstants.CONTROLLER.CONTROLLER_FALSE)
                        viewModel.createWorkout(workout)
                    }

                }
                dialog.setNegativeButton(R.string.cancel) { _, _ ->
                }
                dialog.show()
            }

            override fun onDeleteClick(id: Int) {
                val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(context)
                dialogBuilder.setTitle(R.string.delete_workout_title)
                dialogBuilder.setMessage(R.string.delete_workout_message)
                dialogBuilder.setPositiveButton(R.string.yes) { _, _ ->
                    viewModel.deleteWorkout(id)
                }
                dialogBuilder.setNegativeButton(R.string.no) { _, _ ->

                }
                val dialog = dialogBuilder.create()
                dialog.show()
            }

            override fun onEditClick(workout: Workout) {
                val intent = Intent(context, WorkoutFormActivity::class.java)
                val bundle = Bundle()
                bundle.putInt(MyConstants.KEY.ID_KEY, workout.id)
                intent.putExtras(bundle)
                startActivity(intent)
            }
        }

        adapter.attachListener(listener)

        viewModel.listWorkouts()

        observe()
        return binding.root
    }

    private fun observe(){

        viewModel.createValidation.observe(viewLifecycleOwner){
            if(it.status()){
                showToast(resources.getString(R.string.success_create_workout))
            }else{
                showToast(it.message())
            }
        }

        viewModel.deleteValidation.observe(viewLifecycleOwner) {
            if (it.status()) {
                showToast(resources.getString(R.string.success_delete_workout))
            } else {
                showToast(it.message())
            }
        }

        viewModel.workoutList.observe(viewLifecycleOwner){
            adapter.updateWorkouts(it)
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