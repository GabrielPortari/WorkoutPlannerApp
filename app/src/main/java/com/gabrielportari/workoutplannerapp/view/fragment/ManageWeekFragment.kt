package com.gabrielportari.workoutplannerapp.view.fragment

import android.app.AlertDialog
import android.content.DialogInterface
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
                val dialog : AlertDialog.Builder = AlertDialog.Builder(context)
                dialog.setTitle("Adicionar Semana")
                val view = layoutInflater.inflate(R.layout.dialog_form, null)
                dialog.setView(view)
                dialog.setPositiveButton("Adicionar") { _, _ ->
                    val name = view.findViewById<EditText>(R.id.edit_name).text.toString()
                    val description = view.findViewById<EditText>(R.id.edit_description).text.toString()
                    if(name.isBlank() || description.isBlank() ) {
                        showToast("Preencha todos os campos")
                    }
                    else {
                        val week = Week(weekId, name, description,
                            null, null, null, null, null, null, null,
                            MyConstants.CONTROLLER.CONTROLLER_FALSE)
                        viewModel.createWeek(week)
                    }

                }
                dialog.setNegativeButton("Cancelar") { _, _ ->
                }
                dialog.show()
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

    private fun observe() {
        viewModel.deleteValidation.observe(viewLifecycleOwner) {
            if (it.status()) {
                showToast("Semana deletada com sucesso")
            } else {
                showToast(it.message())
            }
        }

        viewModel.createValidation.observe(viewLifecycleOwner) {
            if (it.status()) {
                if(weekId == 0) {
                    showToast("Semana criada com sucesso")
                }else{
                    showToast("Semana editada com sucesso")
                }
            } else {
                showToast(it.message())
            }
        }
        viewModel.weekList.observe(viewLifecycleOwner) {
            adapter.updateWeeks(it)
        }
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