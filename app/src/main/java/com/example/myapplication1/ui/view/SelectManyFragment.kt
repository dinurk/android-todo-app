package com.example.myapplication1.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import com.example.myapplication1.R
import com.example.myapplication1.ui.stateholders.TasksViewModel

private const val SELECTED_COUNT = "selectedCount"

class SelectManyFragment : Fragment() {

    /** Количество выбранных элементов в списке задач */
    private var selectedCount: Int? = null;

    private val viewModel: TasksViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            selectedCount = it.getString(SELECTED_COUNT)?.toInt()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_select_many, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val deleteAllButton = view.findViewById<Button>(R.id.deleteAllButton)
        deleteAllButton.setText("удалить ($selectedCount)")

        deleteAllButton.setOnClickListener {
            viewModel.deleteSelectedTasks()
        }

        val clearSelectionButton = view.findViewById<Button>(R.id.clearSelectionButton)
        clearSelectionButton.setOnClickListener {
            viewModel.clearSelection()
        }
    }

//    companion object {
//        @JvmStatic
//        fun newInstance(selectedCount: String) = SelectManyFragment().apply {
//            arguments = Bundle().apply {
//                putString(SELECTED_COUNT, selectedCount)
//            }
//        }
//    }
}