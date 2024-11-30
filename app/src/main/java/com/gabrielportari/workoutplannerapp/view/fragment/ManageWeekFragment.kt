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
import com.gabrielportari.workoutplannerapp.R
import com.gabrielportari.workoutplannerapp.data.constants.MyConstants
import com.gabrielportari.workoutplannerapp.data.listener.WeekListener
import com.gabrielportari.workoutplannerapp.data.model.Week
import com.gabrielportari.workoutplannerapp.databinding.FragmentManageWeekBinding
import com.gabrielportari.workoutplannerapp.view.activity.WeekFormActivity
import com.gabrielportari.workoutplannerapp.view.adapter.WeekAdapter
import com.gabrielportari.workoutplannerapp.viewmodel.ManageWeekViewModel


class ManageWeekFragment : Fragment() {

    private lateinit var viewModel: ManageWeekViewModel
    private var _binding: FragmentManageWeekBinding? = null
    private val binding get() = _binding!!

    private var weekId = 0
    private val adapter = WeekAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[ManageWeekViewModel::class.java]
        _binding = FragmentManageWeekBinding.inflate(inflater, container, false)

        binding.recyclerWeeks.layoutManager = LinearLayoutManager(context)
        binding.recyclerWeeks.adapter = adapter

        val listener = object: WeekListener {
            override fun onNewClick() {
                val intent = Intent(context, WeekFormActivity::class.java)
                startActivity(intent)
            }
            override fun onDeleteClick(id: Int) {
                val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(context)
                dialogBuilder.setTitle("Deletar Treino")
                dialogBuilder.setMessage("Tem certeza que deseja excluir esse treino?")
                dialogBuilder.setPositiveButton("Sim") { _, _ ->
                    viewModel.deleteWeek(id)
                }
                dialogBuilder.setNegativeButton("Nao") { _, _ ->

                }
                val dialog = dialogBuilder.create()
                dialog.show()
            }

            override fun onEditClick(week: Week) {
                val intent = Intent(context, WeekFormActivity::class.java)
                val bundle = Bundle()
                bundle.putInt(MyConstants.KEY.ID_KEY, week.id)
                intent.putExtras(bundle)
                startActivity(intent)
            }
        }

        adapter.attachListener(listener)

        viewModel.listWeeks()
        observe()
        return binding.root
    }

    fun observe(){

    }

    override fun onResume() {
        super.onResume()
        viewModel.listWeeks()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showToast(message: String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}