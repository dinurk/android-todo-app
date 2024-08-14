package com.example.myapplication1

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication1.data.repository.TasksRepository
import com.example.myapplication1.ui.stateholders.TasksViewModel
import com.example.myapplication1.ui.view.DefaultControlsFragment
import com.example.myapplication1.ui.view.SelectManyFragment
import com.example.myapplication1.ui.view.TasksAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

class MainActivity : AppCompatActivity(), TasksAdapter.TaskSelectHandler {

    /** Инжектим репозиторий задач */
    @Inject
    lateinit var tasksRepository: TasksRepository

    /** ViewModel */
    private val tasksViewModel: TasksViewModel by viewModels {
        TasksViewModel.provideFactory(tasksRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /** Установка фрагмента в нижнем меню */
        if (savedInstanceState == null) {
            setBottomMenuFragment(DefaultControlsFragment())
        }

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        /** Создание и настройка recyclerView */
        val recyclerView: RecyclerView = findViewById(R.id.taskList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val tasksAdapter = TasksAdapter(emptyList(), this)
        recyclerView.adapter = tasksAdapter

        /** Подключаем DI */
        (this.application as MyApplication).appComponent.inject(this)

        lifecycleScope.launch(Dispatchers.IO) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                tasksViewModel.tasks().collect {
                    Log.d("List info", it.toString())
                    Log.d("Thread info", Thread.currentThread().name)
                    withContext(Dispatchers.Main) {
                        tasksAdapter.setData(it)
                        Log.d("Thread info", Thread.currentThread().name)
                    }
                }
            }
        }

        lifecycleScope.launch {
            tasksViewModel.selectedCount().collect { selectedCount ->
                if (selectedCount > 0) {
                    val fragmentArguments = Bundle().apply {
                        putString(
                            "selectedCount",
                            selectedCount.toString()
                        )
                    }
                    val fragment = SelectManyFragment().apply {
                        arguments = fragmentArguments
                    }
                    setBottomMenuFragment(fragment)
                } else {
                    setBottomMenuFragment(DefaultControlsFragment())
                }
            }
        }
    }

    /** Изменить фрагмент для нижнего меню */
    private fun setBottomMenuFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.bottomControls, fragment)
        }
    }

    /** Обработчик выделения задачи */
    override fun onTaskSelect(uuid: UUID, selected: Boolean) {
        tasksViewModel.setSelection(uuid, selected)
    }
}
