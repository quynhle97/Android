package com.example.week6

import android.app.Activity
import android.arch.persistence.room.Room
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.bumptech.glide.Glide
import com.example.week6.ROOM.User
import com.example.week6.ROOM.UserDAO
import com.example.week6.ROOM.UserDatabase
import kotlinx.android.synthetic.main.activity_detail_task.*
import kotlinx.android.synthetic.main.item_task.*

class DetailTaskActivity : AppCompatActivity() {
    val spinnerData = ArrayList<Pair<String, Int>>()
    var users: ArrayList<User> = ArrayList()
    lateinit var userDao: UserDAO
    lateinit var db: UserDatabase
    var userId = "Unassigned"
    var userIntentId = -1
    var userAssignedName = "unassigned"
    var userPosition = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_task)

        initUserDatabase()
        getAndPutData()
        db = UserDatabase.invoke(this)
        setupSpinner()
        handleSaveData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_task_settings, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_detail_activity -> {
            val builder = AlertDialog.Builder(this@DetailTaskActivity)
            builder.setTitle("Confirmation")
                .setMessage("Are you sure to remove this task?")
                .setPositiveButton("OK") { _, _ ->
                    val intent = Intent()
                    intent.putExtra(TASK_POSITION_KEY, userPosition)
                    intent.putExtra(TASK_DELETE_KEY, 1)
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }
                .setNegativeButton(
                    "Cancel"
                ) { dialog, _ -> dialog?.dismiss() }

            val myDialog = builder.create();
            myDialog.show()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun handleSaveData() {
        btnSave.setOnClickListener {
            val intent = Intent()
            intent.putExtra(TASK_POSITION_KEY, userPosition)
            intent.putExtra(TASK_ID_KEY, userIntentId)
            intent.putExtra(TASK_DESCRIPTION_KEY, tvTitle.text)
            intent.putExtra(TASK_COMPLETED_KEY, checkBox.isChecked)
            intent.putExtra(TASK_ASSIGNED_KEY, userAssignedName)

            intent.putExtra(TASK_DELETE_KEY, 0)
            setResult(Activity.RESULT_OK, intent)
            finish()
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

    private fun getAndPutData() {
        val data = intent.extras
        if (data != null) {
            val description = data.getString(TASK_DESCRIPTION_KEY)
            val completed = data.getBoolean(TASK_COMPLETED_KEY)
            userId = data.getString(TASK_ASSIGNED_KEY)
            userPosition = data.getInt(TASK_POSITION_KEY)
            userIntentId = data.getInt(TASK_ID_KEY)

            tvTitle.text = description
            checkBox.isChecked = completed
            tvAssigned.text = userId

        }
    }

    private fun getUsers() {
        val users = userDao.getAll()

        this.users.addAll(users)

        spinnerData.add(Pair("Assign Task", -1))
        for (item in users) {
            spinnerData.add(Pair(item.name, item.id) as Pair<String, Int>)
        }
        spinnerData.add(Pair("Unassign", -1))
    }

    private fun setupSpinner() {
        getUsers()
        val spinnerAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerData.map{
            if (it.second >= 0)
                "${it.first} (id = ${it.second})"
            else
                it.first
        })
        spinner.adapter = spinnerAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                tvAssigned.text = userId
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                userAssignedName = spinnerData[position].first
            }
        }
    }
}
