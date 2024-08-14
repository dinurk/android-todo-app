package com.example.myapplication1.data.datasource

import com.example.myapplication1.data.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID
import javax.inject.Inject

class TasksLocalDataSource @Inject constructor() {
    private var tasks: MutableList<Task> =
        mutableListOf(Task(UUID.randomUUID(), "task1"), Task(UUID.randomUUID(), "task1"))

    fun getTasks(): Flow<List<Task>> = flow {
        emit(tasks as List<Task>)
    }

    fun addTask(task: Task) {
        tasks.add(task)
    }

    fun deleteTasks(tasksToDelete: Set<UUID>) {
        tasks = tasks.filter {
            !tasksToDelete.contains(it.uuid)
        }.toMutableList()
    }
}