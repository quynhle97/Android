package com.example.week6

import android.annotation.SuppressLint
import android.app.Activity
import android.arch.persistence.room.Room
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import com.example.week6.RECYCLERVIEW.*
import com.example.week6.ROOM.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_user.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule

@SuppressLint("SetTextI18n")

class UserActivity : AppCompatActivity() {
    var users: ArrayList<User> = ArrayList()
    lateinit var userAdapter: UserAdapter
    lateinit var userDao: UserDAO
    lateinit var db: UserDatabase

    var tasks: java.util.ArrayList<Task> = java.util.ArrayList()
    lateinit var taskDao: TaskDAO
    lateinit var dbTask: TaskDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        initUserDatabase()

        setupRecyclerView()

        getUsers()

        db = UserDatabase.invoke(this)

        btnAdd_UserAct.setOnClickListener {
            val userBtn = User()
            userBtn.name = etTitle_UserAct.text.toString()
            val userDao = db.userDAO()
            val id = userDao.insert(userBtn)
            userBtn.id = id.toInt()
            userAdapter.appendData(userBtn)
        }
    }

    private fun computeUserAssignedTasks() {
        val tasks = taskDao.getAll()

        for (item in tasks) {

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.user_settings, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_user_activity -> {
            // Intent UserActivity
            val intent = Intent(this@UserActivity, MainActivity::class.java)
            startActivity(intent)
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun initUserDatabase() {
        val db = Room.databaseBuilder(
            applicationContext,
            UserDatabase::class.java, DATABASE_USER_NAME
        ).allowMainThreadQueries()
            .build()
        userDao = db.userDAO()
    }

    private fun setupRecyclerView() {
        rvUsers_UserAct.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?

        userAdapter = UserAdapter(users, this)

        rvUsers_UserAct.adapter = userAdapter

        userAdapter.setListener(userItemClickListener)
    }

    private fun getUsers() {
        val users = userDao.getAll()

        this.users.addAll(users)

        userAdapter.notifyDataSetChanged()
    }

    private val userItemClickListener = object : UserItemClickListener {
        override fun onItemLongClicked(position: Int) {

        }

        override fun onBtnDeleteClicked(position: Int) {
            val builder = AlertDialog.Builder(this@UserActivity)
            builder.setTitle("Confirmation")
                .setMessage("Are you sure to remove this user?")
                .setPositiveButton("OK") { _, _ ->
                    removeItem(position)
                }
                .setNegativeButton(
                    "Cancel"
                ) { dialog, _ -> dialog?.dismiss() }

            val myDialog = builder.create()
            myDialog.show()
        }
    }

    private fun removeItem(position: Int) {
        userDao.delete(users[position])

        users.removeAt(position)

        userAdapter.notifyItemRemoved(position)
        Timer(false).schedule(500) {
            runOnUiThread {
                userAdapter.setData(users)
                userAdapter.notifyDataSetChanged()
            }
        }
    }
}
