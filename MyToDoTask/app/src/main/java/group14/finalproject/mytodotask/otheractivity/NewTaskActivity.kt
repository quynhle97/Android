package group14.finalproject.mytodotask.otheractivity

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.RadioButton
import android.widget.Toast
import group14.finalproject.mytodotask.*
import group14.finalproject.mytodotask.room.*
import group14.finalproject.mytodotask.sharedpreferences.SharedPreferencesHelper
import kotlinx.android.synthetic.main.activity_task.*
import kotlinx.android.synthetic.main.activity_task.cb_completed
import kotlinx.android.synthetic.main.activity_task.edt_description_note
import kotlinx.android.synthetic.main.activity_task.edt_title
import kotlinx.android.synthetic.main.activity_task.radio_priority_choice
import kotlinx.android.synthetic.main.dialog_add_new_tag.*
import kotlinx.android.synthetic.main.dialog_add_new_tag.view.*
import kotlin.collections.ArrayList

class NewTaskActivity : AppCompatActivity() {
    // Tags Database
    var tags: ArrayList<Tag> = ArrayList()
    lateinit var listTagsName: ArrayList<String>
    lateinit var tagsDao: TagDAO
    lateinit var dbTags: TagDatabase
    var databaseTagName = ""

    // Tasks Database
    var tasks: ArrayList<Task> = ArrayList()
    lateinit var taskDao: TaskDAO
    lateinit var dbTasks: TaskDatabase
    var databaseTaskName = ""

    // Relationship Database
    var checkedTags: ArrayList<Boolean> = ArrayList()
    var relationships: ArrayList<Relationship> = ArrayList()
    lateinit var relationshipDao: RelationshipDAO
    lateinit var dbRelationships: RelationshipDatabase
    var databaseRelationship = ""
    lateinit var listCheckedTags: BooleanArray

    // Others
    val spinnerData = ArrayList<Pair<String, Int>>()

    // Initial Variables
    var title = "Title"
    var description = "Description"
    var date = "28/08/2019"
    var time = "12:12"
    var completed = false
    var priority = 0
    var reminder = "None"
    var locationX = .0f
    var locationY = .0f
    var categorize = "Uncategorized"
    var indexRadioButton = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)

        // Invoke database
        invokeDatabase()
        // Initialize tags
        getInitialAllTags()
        getInitialAllRelationship()

        radio_priority_choice.setOnCheckedChangeListener { group, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            indexRadioButton = group.indexOfChild(radio)
        }

        tv_add_tags.setOnClickListener {
            // Parse from ArrayList<Category> to List<String> for this.tags
            // Initial 2 parts of Tag: TagName and CheckedTag
            listCheckedTags = BooleanArray(tags.size)
            listTagsName = ArrayList(tags.size)
            for (i in tags) listTagsName.add(i.tag)
            val listTags = arrayOfNulls<String>(listTagsName.size)
            listTagsName.toArray(listTags)

            // Initial checkbox value
            val checkedTags = BooleanArray(tags.size)
            for (i in 0 until tags.size) checkedTags[i] = false

            val builder = AlertDialog.Builder(this)
            builder.setMultiChoiceItems(listTags, checkedTags) { dialog, which, isChecked ->
                // Check tag
                // Save temporarily tag state, after btn_save clicked, save tag-note relationship
                Toast.makeText(applicationContext, "$which - $isChecked", Toast.LENGTH_SHORT).show()
                checkedTags[which] = isChecked
            }
            builder.setTitle("List of Tags")
            builder.setCancelable(false)

            builder.setPositiveButton("Save") { dialog, which ->
                listCheckedTags = checkedTags // Save to adapter
                var tmp = ""
                for (i in 0 until tags.size) {
                    if (listCheckedTags[i]) {
                        tmp += listTagsName[i] + ", "
                    }
                }
                tv_add_tags.text = tmp
            }

            builder.setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
            }

            builder.setNeutralButton("Create") { dialog, which ->
                val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_new_tag, null)
                val mBuilder = android.app.AlertDialog.Builder(this)
                    .setView(mDialogView)
                    .setTitle("Create new tag")
                val  mAlertDialog = mBuilder.show()
                mAlertDialog.btnCreateTagDialog.setOnClickListener {
                    mAlertDialog.dismiss()
                    val tagName = mDialogView.edt_tag_name.text.toString()
//                    // Add database
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
            val dialog = builder.create()
            dialog.show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_task, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.action_save-> {
                handleSaveTask()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun invokeDatabase() {
        databaseTaskName = SharedPreferencesHelper.readString(USERNAME_KEY) + DATABASE_TASK_SUFFIX_NAME
        databaseTagName = SharedPreferencesHelper.readString(USERNAME_KEY) + DATABASE_TAG_SUFFIX_NAME
        databaseRelationship = SharedPreferencesHelper.readString(USERNAME_KEY) + DATABASE_RELATIONSHIP_SUFFIX_NAME

        dbTasks = TaskDatabase.invoke(this, databaseTaskName)
        dbTags = TagDatabase.invoke(this, databaseTagName)
        dbRelationships = RelationshipDatabase.invoke(this, databaseRelationship)
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

    private fun addNewTag(newTag: Tag) {
        val tagDao = dbTags.tagDAO()
        val id = tagDao.insert(newTag)
        newTag.id = id.toInt()
    }

    private fun addNewRelationship(newRelationship: Relationship) {
        val relationshipDao = dbRelationships.relationshipDAO()
        val id = relationshipDao.insert(newRelationship)
        newRelationship.id = id.toInt()
    }

    private fun addTagsAdapter(tag: Tag) {
        this.tags.add(tag)
    }

    private fun addTaskForDAO(newTask: Task) : Int{
        val taskDao = dbTasks.taskDAO()
        val id= taskDao.insert(newTask)
        newTask.id = id.toInt()
        return newTask.id!!
    }

    private fun getDataInTaskActivity() {
        title = edt_title.text.toString()
        description = edt_description_note.text.toString()
        completed = cb_completed.isChecked
        categorize = tv_add_tags.text.toString()
        priority = indexRadioButton
    }

    private fun handleSaveTask() {
        getDataInTaskActivity()

        val newTask = Task()
        newTask.title = title
        newTask.date = date
        newTask.time = time
        newTask.checked = completed
        newTask.reminder = reminder
        newTask.description = description
        newTask.priority = priority
        newTask.locationX = locationX
        newTask.locationY = locationY
        newTask.categorize = categorize

        // this id save with tag id to save database relationship
        newTask.id = addTaskForDAO(newTask)
        handleSaveRelationship(newTask.id!!)

        val intent = Intent()
        intent.putExtra(TASK_ID_KEY, newTask.id!!)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun handleSaveRelationship(idTask: Int) {
        val tagDao = dbTags.tagDAO()
        for (i in 0 until tags.size) {
            if (listCheckedTags[i]) {
                val tag = tagDao.findByTag(listTagsName[i])
                if (tag.id != null) {
                    addNewRelationship(Relationship(null, tag.id!!, idTask))
                    Toast.makeText(applicationContext, "Save Tag added: ${tag.id} - $idTask", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}
