package com.gabrielportari.workoutplannerapp.view.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.gabrielportari.workoutplannerapp.data.constants.MyConstants
import com.gabrielportari.workoutplannerapp.data.listener.WeekListener
import com.gabrielportari.workoutplannerapp.data.model.Week
import com.gabrielportari.workoutplannerapp.databinding.WeekItemBinding

class WeekViewHolder(private val itemBinding: WeekItemBinding, val listener: WeekListener) :
    RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(week: Week) {
            if (week.controller != MyConstants.CONTROLLER.CONTROLLER_TRUE) {
                itemBinding.imageNewWeek.visibility = View.GONE
                itemBinding.layoutWeekItem.visibility = View.VISIBLE

                itemBinding.textWeekName.text = week.name
                itemBinding.textWeekDescription.text = week.description
            } else {
                itemBinding.imageNewWeek.visibility = View.VISIBLE
                itemBinding.layoutWeekItem.visibility = View.GONE
            }

            itemBinding.imageDeleteWeek.setOnClickListener {
                listener.onDeleteClick(week.id)
            }
            itemBinding.layoutWeekEdit.setOnClickListener {
                listener.onEditClick(week)
            }
            itemBinding.imageNewWeek.setOnClickListener {
                listener.onNewClick()
            }
        }
}