package com.example.week6

import android.annotation.SuppressLint
import android.app.Activity
import android.arch.persistence.room.Room
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.week6.RECYCLERVIEW.TaskAdapter
import com.example.week6.RECYCLERVIEW.TaskItemClickListener
import com.example.week6.ROOM.Task
import com.example.week6.ROOM.TaskDAO
import com.example.week6.ROOM.TaskDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_task.*
import java.util.*
import kotlin.concurrent.schedule

@SuppressLint("SetTextI18n")

class MainActivity : AppCompatActivity() {

    var tasks: ArrayList<Task> = ArrayList()
    lateinit var taskAdapter: TaskAdapter
    lateinit var taskDao: TaskDAO
    lateinit var db: TaskDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initTaskDatabase()

        setupRecyclerView()

        getTasks()

        db = TaskDatabase.invoke(this) // get Room database instance

        btnAdd_UserAct.setOnClickListener {
            val taskBtn = Task()
            taskBtn.description = etTitle_UserAct.text.toString()
            taskBtn.completed = false
            taskBtn.userid = "Unassigned"
            val taskDao = db.taskDAO()
            val id= taskDao.insert(taskBtn)
            taskBtn.id = id.toInt()
            taskAdapter.appendData(taskBtn)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.task_settings, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_settings -> {
            // Intent UserActivity
            val intent = Intent(this@MainActivity, UserActivity::class.java)
            startActivity(intent)
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun initTaskDatabase() {
        val db = Room.databaseBuilder(
            applicationContext,
            TaskDatabase::class.java, DATABASE_TASK_NAME
        ).allowMainThreadQueries()
            .build()
        taskDao = db.taskDAO()
    }

    private fun setupRecyclerView() {
        rvUsers_UserAct.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?

        taskAdapter = TaskAdapter(tasks, this)

        rvUsers_UserAct.adapter = taskAdapter

        taskAdapter.setListener(taskItemClickListener)
    }

    private fun getTasks() {
        val tasks = taskDao.getAll()

        this.tasks.addAll(tasks)

        taskAdapter.notifyDataSetChanged()
    }

    private val taskItemClickListener = object : TaskItemClickListener {
        override fun onItemCLicked(position: Int) {
            val intent = Intent(this@MainActivity, DetailTaskActivity::class.java)
            intent.putExtra(TASK_ID_KEY, tasks[position].id)
            intent.putExtra(TASK_DESCRIPTION_KEY, tasks[position].description)
            intent.putExtra(TASK_ASSIGNED_KEY, tasks[position].userid)
            intent.putExtra(TASK_COMPLETED_KEY, tasks[position].completed)
            intent.putExtra(TASK_POSITION_KEY, position)
            startActivityForResult(intent, CODE_ADD_NEW_STUDENT)
        }

        override fun onItemLongCLicked(position: Int) {

        }

        override fun onEditIconClicked(position: Int) {

        }
    }

    private fun updateItem(position: Int, task: Task) {
        taskDao.update(task)

        tasks.set(position, task)

        taskAdapter.notifyItemChanged(position)
        Timer(false).schedule(500) {
            runOnUiThread {
                taskAdapter.setData(tasks)
                taskAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun removeItem(position: Int) {
        taskDao.delete(tasks[position])

        tasks.removeAt(position)

        taskAdapter.notifyItemRemoved(position)
        Timer(false).schedule(500) {
            runOnUiThread {
                taskAdapter.setData(tasks)
                taskAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CODE_ADD_NEW_STUDENT && resultCode == Activity.RESULT_OK) {
            val test = data?.extras?.getInt(TASK_DELETE_KEY)
            val position = data?.extras?.getInt(TASK_POSITION_KEY)

            if (test == 0) {
                val id = data?.extras?.getInt(TASK_ID_KEY)
                val description = data?.extras?.getString(TASK_DESCRIPTION_KEY)
                val completed = data?.extras?.getBoolean(TASK_COMPLETED_KEY)
                val assigned = data?.extras?.getString(TASK_ASSIGNED_KEY)
                if (position != null && description != null && completed != null && assigned != null) {
                    updateItem(position, Task(id, description, completed, assigned))
                    Log.e(
                        "Main_onActivityResult: ",
                        "user:" + assigned + " - id:" + id.toString() + " - com:" + completed.toString()
                    )
                }
            } else {
                if (position != null)
                    removeItem(position)
            }
        }
    }
}
