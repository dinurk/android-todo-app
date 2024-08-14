package com.example.myapplication1.ui.view

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication1.R

class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val taskNameTextView: TextView
    var taskSelectedCheckBox: CheckBox

    init {
        taskNameTextView = view.findViewById(R.id.taskNameTextView)
        taskSelectedCheckBox = view.findViewById(R.id.taskSelectedCheckbox)
    }
}