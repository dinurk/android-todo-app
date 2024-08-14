package com.example.myapplication1.ui.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication1.R
import com.example.myapplication1.ui.model.TaskForView
import java.util.UUID

class TasksAdapter(
    private var dataSet: List<TaskForView>,
    private val taskSelectHandler: TaskSelectHandler
) :
    RecyclerView.Adapter<TaskViewHolder>() {

    interface TaskSelectHandler {
        fun onTaskSelect(uuid: UUID, selected: Boolean)
    }

    /** DiffUtil */
    class TasksCallback(
        private val oldList: List<TaskForView>,
        private val newList: List<TaskForView>
    ) :
        DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].uuid == newList[newItemPosition].uuid
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }

    /** Создание нового элемента в RecyclerView */
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_item, viewGroup, false)

        return TaskViewHolder(view)
    }

    /** Обновление данных элемента RecyclerView с индексом position */
    override fun onBindViewHolder(viewHolder: TaskViewHolder, position: Int) {
        viewHolder.taskNameTextView.text = dataSet[position].text
        /**
         * т.к. View переиспользуются, в т.ч. при добавлении новых элементов,
         * сбрасываем состояние чекбокса, чтобы удаленный чекбокс с выделением случайно не отобразился
         * для вновь созданного элемента
         * */
        viewHolder.taskSelectedCheckBox.isChecked = dataSet[position].selected
    }

    /** При появлении View на экране добавим обработчик на чекбокс */
    override fun onViewAttachedToWindow(viewHolder: TaskViewHolder) {
        super.onViewAttachedToWindow(viewHolder)
        viewHolder.taskSelectedCheckBox.setOnClickListener { checkbox ->

            taskSelectHandler.onTaskSelect(
                dataSet[viewHolder.layoutPosition].uuid,
                (checkbox as CheckBox).isChecked
            )
        }
    }

    /** При скрытиии View с экрана убираем обработчик с чекбокса */
    override fun onViewDetachedFromWindow(viewHolder: TaskViewHolder) {
        super.onViewDetachedFromWindow(viewHolder)
        viewHolder.taskSelectedCheckBox.setOnClickListener(null)
    }

    /** Получить количество элементов */
    override fun getItemCount() = dataSet.size

    /** Установить новый набор данных для отображения в RecyclerView */
    fun setData(newDataSet: List<TaskForView>) {
        val diffUtil = TasksCallback(dataSet, newDataSet)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        diffResults.dispatchUpdatesTo(this)
        dataSet = newDataSet
    }
}