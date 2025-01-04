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
import com.gabrielportari.workoutplannerapp.data.model.User
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
    private var user: User = User(MyConstants.USER_ID.ID, R.string.user_name.toString(), 1)

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
                dialog.setTitle(R.string.new_week_title)
                val view = layoutInflater.inflate(R.layout.dialog_form, null)
                dialog.setView(view)
                dialog.setPositiveButton(R.string.create) { _, _ ->
                    val name = view.findViewById<EditText>(R.id.edit_name).text.toString()
                    val description = view.findViewById<EditText>(R.id.edit_description).text.toString()
                    if(name.isBlank() || description.isBlank() ) {
                        showToast(resources.getString(R.string.fill_all_fields))
                    }
                    else {
                        val week = Week(weekId, name, description,
                            null, null, null, null, null, null, null,
                            MyConstants.CONTROLLER.CONTROLLER_FALSE)
                        viewModel.createWeek(week)
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
                    if(id != user.selectedWeek) {
                        viewModel.deleteWeek(id)
                    }else{
                        showToast(resources.getString(R.string.cant_delete_active_week))
                    }
                }
                dialogBuilder.setNegativeButton(R.string.no) { _, _ ->

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
        viewModel.loadUser()
        observe()
        return binding.root
    }

    private fun observe() {
        viewModel.user.observe(viewLifecycleOwner) {
            user = it
        }

        viewModel.deleteValidation.observe(viewLifecycleOwner) {
            if (it.status()) {
                showToast(resources.getString(R.string.success_delete_week))
            } else {
                showToast(it.message())
            }
        }

        viewModel.createValidation.observe(viewLifecycleOwner) {
            if (it.status()) {
                if(weekId == 0) {
                    showToast(resources.getString(R.string.success_create_week))
                }else{
                    showToast(resources.getString(R.string.success_edit_week))
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