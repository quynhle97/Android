package group14.finalproject.mytodotask

import android.app.Activity
import android.arch.persistence.room.Room
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.support.v4.widget.DrawerLayout
import android.support.design.widget.NavigationView
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.Menu
import android.view.WindowManager
import android.widget.Toast
import group14.finalproject.mytodotask.otheractivity.AboutActivity
import group14.finalproject.mytodotask.otheractivity.DetailsTaskActivity
import group14.finalproject.mytodotask.otheractivity.MultiPurposeActivity
import group14.finalproject.mytodotask.otheractivity.NewTaskActivity
import group14.finalproject.mytodotask.recyclerview.TaskAdapter
import group14.finalproject.mytodotask.recyclerview.TaskItemClickListener
import group14.finalproject.mytodotask.room.*
import group14.finalproject.mytodotask.sharedpreferences.SharedPreferencesHelper
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.dialog_add_new_tag.*
import kotlinx.android.synthetic.main.dialog_add_new_tag.view.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    var tasks: ArrayList<Task> = ArrayList()
    lateinit var taskAdapter: TaskAdapter
    lateinit var taskDao: TaskDAO
    lateinit var dbTasks: TaskDatabase
    var databaseNoteName = ""

    var tags: ArrayList<Tag> = ArrayList()
    lateinit var tagsDao: TagDAO
    lateinit var dbTags: TagDatabase
    var databaseTagName = ""

    var relationships: ArrayList<Relationship> = ArrayList()
    lateinit var relationshipDao: RelationshipDAO
    lateinit var dbRelationship: RelationshipDatabase
    var databaseRelationship = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupTaskDatabase()
        setupTagDatabase()
        setupRelationshipDatabase()

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            val intent = Intent(this@MainActivity, NewTaskActivity::class.java)
            startActivityForResult(intent, CODE_ADD_NEW_TASK)
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)
    }

    private fun initTaskDatabase() {
        val db = Room.databaseBuilder(applicationContext,
            TaskDatabase::class.java, databaseNoteName).allowMainThreadQueries().build()
        taskDao = db.taskDAO()
    }

    private fun initTagDatabase() {
        val db = Room.databaseBuilder(applicationContext,
            TagDatabase::class.java, databaseTagName).allowMainThreadQueries().build()
        tagsDao = db.tagDAO()
    }

    private fun initRelationshipDatabase() {
        val db = Room.databaseBuilder(applicationContext,
            RelationshipDatabase::class.java, databaseRelationship).allowMainThreadQueries().build()
        relationshipDao = db.relationshipDAO()
    }

    private fun getTasksForAdapter() {
        val tasks = taskDao.getAll()

        this.tasks.addAll(tasks)

        taskAdapter.notifyDataSetChanged()
    }

    private fun getTags() {
        val tags = tagsDao.getAll()
        this.tags.addAll(tags)
    }

    private fun getRelationships() {
        val relationships = relationshipDao.getAll()
        this.relationships.addAll(relationships)
    }

    private fun addTagsAdapter(tag: Tag) {
        this.tags.add(tag)
    }

    private fun setupTaskDatabase() {
        databaseNoteName = SharedPreferencesHelper.readString(USERNAME_KEY) + DATABASE_TASK_SUFFIX_NAME
        initTaskDatabase()
        setupRecyclerView()
        getTasksForAdapter()

        dbTasks = TaskDatabase.invoke(this, databaseNoteName) // get Room database instance
    }

    private fun setupTagDatabase() {
        databaseTagName = SharedPreferencesHelper.readString(USERNAME_KEY) + DATABASE_TAG_SUFFIX_NAME
        initTagDatabase()
        getTags()

        dbTags = TagDatabase.invoke(this, databaseTagName)
    }

    private fun setupRelationshipDatabase() {
        databaseRelationship = SharedPreferencesHelper.readString(USERNAME_KEY) + DATABASE_RELATIONSHIP_SUFFIX_NAME
        initRelationshipDatabase()
        getRelationships()

        dbRelationship = RelationshipDatabase.invoke(this, databaseRelationship)
    }

    private fun getTagName(tagId: Int): String {
        val tagDao = dbTags.tagDAO()
        return tagDao.findById(tagId).tag
    }

    private fun addNewTask(newTask: Task) {
        val taskDao = dbTasks.taskDAO()
        val id= taskDao.insert(newTask)
        newTask.id = id.toInt()
        taskAdapter.appendData(newTask)
    }

    private fun addNewTaskForAdapter(newTask: Task) {
        taskAdapter.appendData(newTask)
    }

    private fun addNewTag(newTag: Tag) {
        val tagDao = dbTags.tagDAO()
        val id = tagDao.insert(newTag)
        newTag.id = id.toInt()
    }

    private fun updateTaskForAdapter(position: Int, task: Task) {
        tasks[position] = task

        taskAdapter.notifyItemChanged(position)
        Timer(false).schedule(500) {
            runOnUiThread {
                taskAdapter.setData(tasks)
                taskAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun removeTaskFromAdapter(position: Int) {
        tasks.removeAt(position)

        taskAdapter.notifyItemRemoved(position)
        Timer(false).schedule(500) {
            runOnUiThread {
                taskAdapter.setData(tasks)
                taskAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun removeTagFromAdapter(position: Int) {
        tags.removeAt(position)
    }

    private fun removeItemTask(position: Int) {
        // Remove from TaskDatabase
        taskDao.delete(tasks[position])
        // Remove from AdapterTask
        removeTaskFromAdapter(position)
    }

    private fun removeItemTag(position: Int) {
        tagsDao.delete(tags[position])

        removeTagFromAdapter(position)
    }

    private fun removeItemRelationship(relationship: Relationship) {
        relationshipDao.delete(relationship)
    }

    private fun updateItemTask(position: Int, task: Task) {
        taskDao.update(task)

        updateTaskForAdapter(position, task)
    }

    private fun setupRecyclerView() {
        rcv_list_tasks.layoutManager = LinearLayoutManager(this)
        taskAdapter = TaskAdapter(tasks, this)
        rcv_list_tasks.adapter = taskAdapter
        taskAdapter.setListener(taskItemClickListener)
    }

    private val taskItemClickListener = object : TaskItemClickListener {
        override fun onItemClicked(position: Int) {
            val intent = Intent(this@MainActivity, DetailsTaskActivity::class.java)
            intent.putExtra(CODE_EDIT_TASK_POSITION, position)
            intent.putExtra(TASK_ID_KEY, tasks[position].id)
            startActivityForResult(intent, CODE_EDIT_TASK)
        }

        override fun onItemLongClicked(position: Int) {
            val builder = AlertDialog.Builder(this@MainActivity)
            builder.setTitle("Delete Confirmation")
                .setMessage("Are you sure to remove this task?")
                .setPositiveButton("OK") { _, _ ->
                    val task = tasks[position]
                    for (i in relationships) {
                        if (i.note == task.id) {
                            removeItemRelationship(i)
                        }
                    }
                    removeItemTask(position)
                }
                .setNegativeButton(
                    "Cancel"
                ) { dialog, _ -> dialog?.dismiss() }
            val myDialog = builder.create()
            myDialog.show()
        }

        override fun onCheckBoxClicked(position: Int, state: Boolean) {
            tasks[position].checked = state
            updateItemTask(position, tasks[position])
        }
    }

    override fun onBackPressed() {
        taskDao.deleteAllTask()
        tagsDao.deleteAllTags()
        relationshipDao.deleteAllRelations()
        val intent = Intent(this@MainActivity, FirstActivity::class.java)
        startActivity(intent)
        Toast.makeText(applicationContext,"SIGN OUT", Toast.LENGTH_SHORT).show()
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                val intent = Intent(this@MainActivity, MultiPurposeActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.action_clear_completed->true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_my_tasks -> {

            }
            R.id.nav_new_tag -> {
                val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_new_tag, null)
                val mBuilder = android.app.AlertDialog.Builder(this)
                    .setView(mDialogView)
                    .setTitle("Create new tag")
                val  mAlertDialog = mBuilder.create()
                mAlertDialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
                mAlertDialog.show()
                mAlertDialog.btnCreateTagDialog.setOnClickListener {
                    mAlertDialog.dismiss()
                    val tagName = mDialogView.edt_tag_name.text.toString()
                    // Add database
                    val newTag = Tag()
                    newTag.tag = tagName
                    addNewTag(newTag)
                    addTagsAdapter(newTag)
                    Toast.makeText(applicationContext,"New tag added: $tagName", Toast.LENGTH_SHORT).show()
                }
                mAlertDialog.btnCancelDialog.setOnClickListener{
                    mAlertDialog.dismiss()
                }
            }
            R.id.nav_list_tag -> {
                showDialogListOfTags()
            }
            R.id.nav_new_filter -> {

            }
            R.id.nav_uncategorized -> {

            }
            R.id.nav_settings -> {

            }
            R.id.nav_about -> {
                val intent = Intent(this@MainActivity, AboutActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_sign_out -> {
                taskDao.deleteAllTask()
                tagsDao.deleteAllTags()
                relationshipDao.deleteAllRelations()
                val intent = Intent(this@MainActivity, FirstActivity::class.java)
                startActivity(intent)
            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CODE_ADD_NEW_TASK && resultCode == Activity.RESULT_OK) {
            val id = data?.extras?.getInt(TASK_ID_KEY)
            if (id != null) {
                val task = taskDao.findById(id)
                addNewTaskForAdapter(task)
            }
        }
        if (requestCode == CODE_EDIT_TASK && resultCode == Activity.RESULT_OK) {
            val id = data?.extras?.getInt(TASK_ID_KEY)
            val codeEditTask = data?.extras?.getInt(CODE_EDIT_TASK_POSITION)
            val codeDelTask = data?.extras?.getInt(CODE_DELETE_TASK_POSITION)
            if (id != null && codeEditTask != null && codeDelTask == -1) {
                val task = taskDao.findById(id)
                updateTaskForAdapter(codeEditTask, task) // code_edit_task = position of edit task
            }
            if (id != null && codeEditTask == -1 && codeDelTask != null) {
                removeTaskFromAdapter(codeDelTask)
            }
        }
    }

    private fun createBaseDialog(title: String, posBtn: String, negBtn: String, description: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(description)
        builder.setPositiveButton(posBtn){dialog, which ->
        }

        builder.setNegativeButton(negBtn){dialog,which ->
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showDialogListOfTags() {
        this.tags = ArrayList()
        getTags()
        this.relationships = ArrayList()
        getRelationships()

        // Save flag as tag can't be deleted: True - Can't be deleted and False - Can be deleted
        var arrTaged = ArrayList<Boolean>(tags.size)
        for (i in 0 until tags.size) {
            arrTaged.add(false)
        }
        for (i in 0 until arrTaged.size) {
            for (j in 0 until relationships.size)
            if (getTagName(relationships[j].tag) == tags[i].tag) {
                arrTaged[i] = true
            }
        }

        val array = ArrayList<String>()
        for (i in tags) {
            array.add(i.tag)
        }
        val listTags = arrayOfNulls<String>(array.size)
        array.toArray(listTags)
        var checkedTags = BooleanArray(tags.size)

        for (i in 0 until tags.size) {
            checkedTags[i] = false
        }

        val builder = AlertDialog.Builder(this)
        builder.setMultiChoiceItems(listTags, checkedTags) { dialog, which, isChecked ->
            Toast.makeText(applicationContext, "$which - $isChecked", Toast.LENGTH_SHORT).show()
            checkedTags[which] = isChecked
        }

        builder.setCancelable(false)
        builder.setTitle("List of Tags")

        builder.setPositiveButton("Delete") { dialog, which ->
            // delete tag from Database Tag
            for (i in 0 until tags.size) {
                if (checkedTags[i] && !arrTaged[i]) {
                    // delete database tags
                        removeItemTag(i)
                }
            }
            Toast.makeText(applicationContext, "Tag attached won't be deleted", Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton("Cancel") { dialog, which ->

        }

        val dialog = builder.create()
        dialog.show()
    }
}
