package group14.finalproject.mytodotask.otheractivity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.RadioButton
import android.widget.Toast
import group14.finalproject.mytodotask.*
import group14.finalproject.mytodotask.recyclerview.TaskAdapter
import group14.finalproject.mytodotask.recyclerview.TaskItemClickListener
import group14.finalproject.mytodotask.room.*
import group14.finalproject.mytodotask.sharedpreferences.SharedPreferencesHelper
import kotlinx.android.synthetic.main.activity_multi_purpose.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule

class MultiPurposeActivity : AppCompatActivity() {
    // Task Database
    var tasks: ArrayList<Task> = ArrayList()
    lateinit var taskAdapter: TaskAdapter
    lateinit var dbTasks: TaskDatabase
    lateinit var taskDao: TaskDAO
    var databaseTaskName = ""

    // Tag Database
    var tags: ArrayList<Tag> = ArrayList()
    lateinit var listTagsName: ArrayList<String>
    lateinit var tagsDao: TagDAO
    lateinit var dbTags: TagDatabase
    var databaseTagName = ""

    // Relationship Database
    var relationships: ArrayList<Relationship> = ArrayList()
    lateinit var relationshipDao: RelationshipDAO
    lateinit var dbRelationships: RelationshipDatabase
    var databaseRelationship = ""
    lateinit var listCheckedTags: BooleanArray
    var checkedTags: ArrayList<Boolean> = ArrayList()

    // Save requests
    var reqTitle = ""
    lateinit var reqTags: ArrayList<Tag>

    // Flags
    var indexRadioButton = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multi_purpose)

        setupDatabase()
        tasks = getAllTasks()
        setupRecyclerView()

        getInitialAllTags()
        getInitialAllRelationship()

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radio: RadioButton = group.findViewById(checkedId)
            indexRadioButton = group.indexOfChild(radio)
        }

        val taskTag = ArrayList<Task>()
        var tmpTv = ""

        tv_select_tags.setOnClickListener {
            listTagsName = ArrayList(tags.size)
            val checkedTags = BooleanArray(tags.size)
            tv_select_tags.text = ""
            tmpTv = ""

            for (i in 0 until checkedTags.size) checkedTags[i] = false
            for (i in tags) listTagsName.add(i.tag)
            val listTags = arrayOfNulls<String>(listTagsName.size)
            listTagsName.toArray(listTags)

            val builder = AlertDialog.Builder(this)
            builder.setMultiChoiceItems(listTags, checkedTags) { dialog, which, isChecked ->
//                Toast.makeText(applicationContext, "Tag: $which - State: $isChecked - Tag: ${listTags[which]}", Toast.LENGTH_SHORT).show()
                checkedTags[which] = isChecked
            }

            builder.setCancelable(false)
            builder.setTitle("List of Tags")

            val listRel = ArrayList<Relationship>()

            builder.setPositiveButton("OK") { dialog, which ->
                for (i in 0 until checkedTags.size) {
                    if (checkedTags[i]) {
                        val idTag = getTag(listTags[i]!!)
                        val tmp = getAllRelationships(idTag.id!!)
                        val listTagTmp = getArrayListStringTask(tmp)

                        for (j in listTagTmp)


                        tmpTv += idTag.tag + ", "
                    }
                }

                for (i in listRel) {
                    val task = getTask(i)
                    if (!(taskTag.contains(task))) {
                        taskTag.add(task)
                    }
                }
                tv_select_tags.text = tmpTv
//                Toast.makeText(applicationContext, "$taskTag", Toast.LENGTH_SHORT).show()
            }

            builder.setNegativeButton("Cancel") { dialog, which ->

            }

            val dialog = builder.create()
            dialog.show()
        }

        var finalTask: ArrayList<Task>

        btn_search.setOnClickListener {
            var taskCom = ArrayList<Task>()
            val taskTitle = ArrayList<Task>()
            finalTask = ArrayList()

            // title
            reqTitle = edt_Title.text.toString()
            if (reqTitle != "") {
                for (i in tasks) {
                    if (i.title.contains(reqTitle, ignoreCase = false)) {
                        taskTitle.add(i)
                    }
                }
            }

            val taskCompleted: List<Task>
            val taskUncompleted: List<Task>
            // check completed
            when (indexRadioButton) {
                0 -> {
                    taskCompleted = taskDao.findAllTasksWithState(true)
                    taskCom = convertListToArrayList(taskCompleted)
                }
                1 -> {
                    taskUncompleted = taskDao.findAllTasksWithState(false)
                    taskCom = convertListToArrayList(taskUncompleted)
                }
                else -> {
                    taskCompleted = taskDao.findAllTasksWithState(true)
                    taskUncompleted = taskDao.findAllTasksWithState(false)
                    for (i in taskCompleted) taskCom.add(i)
                    for (i in taskUncompleted) taskCom.add(i)
                }
            }

            if (edt_Title.text.toString() != "") {
                if (taskTitle.size > 0) {
                    for (i in taskTitle) {
                        if (tv_select_tags.text != "") {
                            if (taskCom.contains(i) && taskTag.contains(i))
                                finalTask.add(i)
                        } else {
                            if (taskCom.contains(i))
                                finalTask.add(i)
                        }
                    }
                } else {
                    // Have title.text but no search for title return null
                    finalTask = ArrayList()
                }
            } else if (tv_select_tags.text != "") {
                // No title and have tags with unknown size
                // Have tags, tags have tasks
                if (taskTag.size > 0) {
                    for (i in taskTag) {
                        if (taskCom.contains(i)) {
                            finalTask.add(i)
                        }
                    }
                } else {
                    finalTask = ArrayList()
                }
            } else {
                for (i in tasks) {
                    if (taskCom.contains(i))
                        finalTask.add(i)
                }
            }
            setTasksAdapter(taskTag)
            // tags
//            Toast.makeText(applicationContext,"${str1.contains(str2, ignoreCase = true)}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupDatabase() {
        databaseTaskName = SharedPreferencesHelper.readString(USERNAME_KEY) + DATABASE_TASK_SUFFIX_NAME
        databaseTagName = SharedPreferencesHelper.readString(USERNAME_KEY) + DATABASE_TAG_SUFFIX_NAME
        databaseRelationship = SharedPreferencesHelper.readString(USERNAME_KEY) + DATABASE_RELATIONSHIP_SUFFIX_NAME

        dbTasks = TaskDatabase.invoke(this, databaseTaskName)
        dbTags = TagDatabase.invoke(this, databaseTagName)
        dbRelationships = RelationshipDatabase.invoke(this, databaseRelationship)

        taskDao = dbTasks.taskDAO()
    }

    private fun getAllTasks(): ArrayList<Task> {
        val arr = ArrayList<Task>()
        val listArr = taskDao.getAll()
        for (i in listArr) {
            arr.add(i)
        }
        return arr
    }

    private fun getTag(tag: String): Tag {
        val tagDao = dbTags.tagDAO()
        return tagDao.findByTag(tag)
    }

    private fun getTagWithId(id: Int): Tag {
        val tagDao = dbTags.tagDAO()
        return tagDao.findById(id)
    }

    private fun getTask(rel: Relationship): Task {
        return taskDao.findById(rel.note)
    }

    private fun getAllRelationships(idTag: Int): List<Relationship> {
        val relationshipDao = dbRelationships.relationshipDAO()
        return relationshipDao.findAllRelationshipsWithTag(idTag)
    }

    private fun setupRecyclerView() {
        rcv_items_search.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager
        taskAdapter = TaskAdapter(tasks, this)
        rcv_items_search.adapter = taskAdapter
        taskAdapter.setListener(taskItemClickListener)
    }

    private val taskItemClickListener = object : TaskItemClickListener {
        override fun onItemClicked(position: Int) {

        }

        override fun onItemLongClicked(position: Int) {
        }

        override fun onCheckBoxClicked(position: Int, state: Boolean) {
        }
    }

    private fun convertListToArrayList(list: List<Task>): ArrayList<Task> {
        var arrList = ArrayList<Task>()
        for (i in list) {
            arrList.add(i)
        }
        return arrList
    }

    private fun setTasksAdapter(allTasks: ArrayList<Task>) {
        Timer(false).schedule(500) {
            runOnUiThread {
                taskAdapter.setData(allTasks)
                taskAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun getInitialAllTags() {
        val tagsDao = dbTags.tagDAO()
        val tags = tagsDao.getAll()
        this.tags.addAll(tags)
    }

    private fun getInitialAllRelationship() {
        val relationshipDao = dbRelationships.relationshipDAO()
        val relationships = relationshipDao.getAll()
        this.relationships.addAll(relationships)
    }

    private fun getArrayListStringTask(relation: List<Relationship>): ArrayList<Task> {
        var tasks = ArrayList<Task>()
        for (i in 0 until relation.size) {
            val id = relation[i].note
            val b = taskDao.findById(id)
            tasks.add(b)
        }
        return tasks
    }
}
