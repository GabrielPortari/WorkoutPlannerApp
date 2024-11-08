package com.gabrielportari.workoutplannerapp.service.helper

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/*
 * Copyright (c) 2021.
 * Created by NaRan
 */

class EmptyDataObserver constructor(rv: RecyclerView?, view: View?) : RecyclerView.AdapterDataObserver() {

    private var emptyView: View? = null
    private var recyclerView: RecyclerView? = null

    init{
        recyclerView = rv
        emptyView = view
        checkIfEmpty()
    }

    private fun checkIfEmpty(){
        if(emptyView != null && recyclerView!!.adapter != null){
            val emptyViewVisible = recyclerView!!.adapter!!.itemCount == 0
            emptyView!!.visibility = if (emptyViewVisible) View.VISIBLE else View.GONE
            recyclerView!!.visibility = if (emptyViewVisible) View.GONE else View.VISIBLE
        }
    }

    override fun onChanged(){
        super.onChanged()
        checkIfEmpty()
    }

    override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
        super.onItemRangeChanged(positionStart, itemCount)
    }
}