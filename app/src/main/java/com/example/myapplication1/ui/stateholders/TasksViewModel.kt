package com.example.myapplication1.ui.stateholders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication1.data.model.Task
import com.example.myapplication1.data.repository.TasksRepository
import com.example.myapplication1.ui.model.TaskForView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class TasksViewModel(private val tasksRepository: TasksRepository) : ViewModel() {

    /** Companion Object, предоставляющий фабрику для создания ViewModel */
    companion object {
        fun provideFactory(
            tasksRepository: TasksRepository
        ): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return TasksViewModel(tasksRepository) as T
                }
            }
    }

    /** Список задач */
    private val tasks = MutableStateFlow<List<TaskForView>>(emptyList())

    /** Количество выделенных элементов списка */
    private val selectedCount = MutableStateFlow(0)

    /** UUID выделенных элементов списка */
    private val selectedSet: HashSet<UUID> = hashSetOf()

    /** Блок инициализации */
    init {
        refresh()
    }

    /** Получить состояние списка задач */
    fun tasks(): StateFlow<List<TaskForView>> {
        return tasks
    }

    /** Получить состояние выделения задач */
    fun selectedCount(): StateFlow<Int> {
        return selectedCount
    }

    /** Обновить список задач из репозитория */
    private fun refresh() {
        viewModelScope.launch(Dispatchers.IO) {
            tasks.value = tasksRepository.getTasks().map {
                TaskForView(
                    it.uuid,
                    it.text,
                    selectedSet.contains(it.uuid)
                )
            }
        }
    }

    /** Добавить задачу в список */
    fun addTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            tasksRepository.addTask(task)
            refresh()
        }
    }

    /** Установить выделение для задачи */
    fun setSelection(taskUuid: UUID, selected: Boolean) {
        if (selected) {
            selectedSet.add(taskUuid)
        } else {
            selectedSet.remove(taskUuid)
        }
        selectedCount.value = selectedSet.size
        refresh()
    }

    /** Удалить задачи из списка */
    fun deleteTasks(tasksToDelete: Set<UUID>) {
        viewModelScope.launch(Dispatchers.IO) {
            tasksRepository.deleteTasks(tasksToDelete)
            selectedSet.clear()
            selectedCount.value = 0
            refresh()
        }
    }

    /** Удалить выделенные задачи из списка */
    fun deleteSelectedTasks() {
        deleteTasks(selectedSet)
    }

    /** Снять выделение */
    fun clearSelection() {
        selectedSet.clear()
        selectedCount.value = 0
        refresh()
    }
}