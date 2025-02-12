package com.gabrielportari.workoutplannerapp.view.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
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
import com.gabrielportari.workoutplannerapp.data.listener.SelectWorkoutListener
import com.gabrielportari.workoutplannerapp.data.model.User
import com.gabrielportari.workoutplannerapp.databinding.FragmentHomeBinding
import com.gabrielportari.workoutplannerapp.view.activity.HelpActivity
import com.gabrielportari.workoutplannerapp.view.activity.SelectWeekActivity
import com.gabrielportari.workoutplannerapp.view.activity.ShowWorkoutActivity
import com.gabrielportari.workoutplannerapp.view.adapter.HomeAdapter
import com.gabrielportari.workoutplannerapp.viewmodel.HomeViewModel


class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var user: User

    private val adapter = HomeAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.recyclerHome.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerHome.adapter = adapter

        val listener = object: SelectWorkoutListener {
            override fun onSelect(id: Int) {
                val intent = Intent(context, ShowWorkoutActivity::class.java)
                intent.putExtra(MyConstants.KEY.WORKOUT_ID_KEY, id)
                startActivity(intent)
            }
        }

        binding.imageNameChange.setOnClickListener{
            val dialog : AlertDialog.Builder = AlertDialog.Builder(context)
            dialog.setTitle(R.string.update_name)
            val view = layoutInflater.inflate(R.layout.dialog_name_form, null)
            dialog.setView(view)
            dialog.setPositiveButton(R.string.update) { _, _ ->
                val name = view.findViewById<EditText>(R.id.edit_user_name).text.toString()
                if(name.isBlank()) {
                    showToast(resources.getString(R.string.fill_name))
                } else {
                    user.name = name
                    viewModel.updateName(user)
                    viewModel.loadUser()
                }
            }
            dialog.setNegativeButton(R.string.cancel) { _, _ ->
            }
            dialog.show()
        }

        binding.textNeedHelp.setOnClickListener{
            val intent = Intent(context, HelpActivity::class.java)
            startActivity(intent)
        }

        adapter.attachListener(listener)

        binding.buttonSelectWeek.setOnClickListener{
            val intent = Intent(context, SelectWeekActivity::class.java)
            startActivity(intent)
        }

        viewModel.loadUser()
        observe()
        return binding.root
    }

    private fun observe(){
        viewModel.user.observe(viewLifecycleOwner){
            user = it
            binding.textHelloUser.text = getString(R.string.hello_user, it.name)
            viewModel.loadWeek(user.selectedWeek)
        }

        viewModel.weekName.observe(viewLifecycleOwner){
            binding.textHomeWeekName.text = it
        }

        viewModel.workouts.observe(viewLifecycleOwner) {
            adapter.updateWorkouts(it)
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.loadUser()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showToast(message: String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }


}