package com.example.myapplication1.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.myapplication1.R
import com.example.myapplication1.data.model.Task
import com.example.myapplication1.ui.stateholders.TasksViewModel
import java.util.UUID

class DefaultControlsFragment : Fragment() {

    private val viewModel: TasksViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_default_controls, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val taskNameInput = requireView().findViewById<EditText>(R.id.taskNameInput)
        val addTaskButton = requireView().findViewById<Button>(R.id.addTaskButton)

        addTaskButton.setOnClickListener {
            if (taskNameInput.text.toString().isEmpty()) {
                Toast.makeText(context, "Введите наименование задачи!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.addTask(
                Task(
                    UUID.randomUUID(),
                    taskNameInput.text.toString()
                )
            )
        }
    }

//    companion object {
//        /** @return A new instance of fragment DefaultControlsFragment. */
//        @JvmStatic
//        fun newInstance() =
//            DefaultControlsFragment()
//    }
}