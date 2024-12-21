package com.gabrielportari.workoutplannerapp.view.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.gabrielportari.workoutplannerapp.data.constants.MyConstants
import com.gabrielportari.workoutplannerapp.data.listener.SelectWeekListener
import com.gabrielportari.workoutplannerapp.data.model.Week
import com.gabrielportari.workoutplannerapp.databinding.SelectWeekItemBinding

class SelectWeekViewHolder(private val itemBinding: SelectWeekItemBinding, val listener: SelectWeekListener):
        RecyclerView.ViewHolder(itemBinding.root){
            fun bind(week: Week) {
                if(week.controller != MyConstants.CONTROLLER.CONTROLLER_TRUE){
                    itemBinding.textSelectWeekName.text = week.name
                    itemBinding.textSelectWeekDescription.text = week.description
                }
                itemBinding.layoutSelectWeek.setOnClickListener {
                    listener.onSelect(week.id)
                }

            }
        }