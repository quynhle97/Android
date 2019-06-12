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
import kotlinx.android.synthetic.main.activity_details_task.cb_completed
import kotlinx.android.synthetic.main.activity_details_task.edt_description_note
import kotlinx.android.synthetic.main.activity_details_task.edt_title
import kotlinx.android.synthetic.main.activity_details_task.radio_priority_choice
import kotlinx.android.synthetic.main.activity_details_task.tv_uncategorized
import kotlinx.android.synthetic.main.dialog_add_new_tag.*
import kotlinx.android.synthetic.main.dialog_add_new_tag.view.*

class DetailsTaskActivity : AppCompatActivity() {
    // Task Database
    lateinit var dbTasks: TaskDatabase
    lateinit var taskDao: TaskDAO
    lateinit var editTask: Task
    var databaseTaskName = ""

    // Tag Database
    var tags: ArrayList<Tag> = ArrayList()
    lateinit var listTagsName: ArrayList<String>
    lateinit var tagsDao: TagDAO
    lateinit var dbTags: TagDatabase
    var databaseTagName = ""

    // Relationship Database
    var checkedTags: ArrayList<Boolean> = ArrayList()
    var relationships: ArrayList<Relationship> = ArrayList()
    lateinit var relationshipDao: RelationshipDAO
    lateinit var dbRelationships: RelationshipDatabase
    var databaseRelationship = ""
    lateinit var listCheckedTags: BooleanArray

    var position: Int = -1
    var indexRadioButton = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_task)

        setupDatabase()
        getInitialAllTags()

        val id = intent.extras.getInt(TASK_ID_KEY)
        position = intent.extras.getInt(CODE_EDIT_TASK_POSITION)
        editTask = taskDao.findById(id)

        radio_priority_choice.setOnCheckedChangeListener { group, checkedId ->
            val radio: RadioButton = group.findViewById(checkedId)
            indexRadioButton = group.indexOfChild(radio)
        }

        tv_uncategorized.setOnClickListener {
            // Parse from ArrayList<Category> to List<String> for this.tags
            relationships = ArrayList()
            getInitialAllRelationship()
            tags = ArrayList()
            getInitialAllTags()

            listTagsName = ArrayList(tags.size)
            listCheckedTags = BooleanArray(tags.size)

            for (i in tags) listTagsName.add(i.tag)
            val listTags = arrayOfNulls<String>(listTagsName.size)
            listTagsName.toArray(listTags)

            // Initial checkbox value
            val checkedTags = BooleanArray(tags.size)
            val listRelationships = ArrayList<String>()

            for (i in 0 until relationships.size) {
                if (editTask.id == relationships[i].note) {
                    listRelationships.add(getTagName((relationships[i].tag)))
                }
            }

            for (i in 0 until tags.size) {
                for (j in 0 until listRelationships.size) {
                    if (listRelationships[j] == listTags[i]) {
                        checkedTags[i] = true
                        Toast.makeText(applicationContext, "${checkedTags[i]} - ${listTags[i]}", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            val builder = AlertDialog.Builder(this)
            builder.setMultiChoiceItems(listTags, checkedTags) { dialog, which, isChecked ->
                Toast.makeText(applicationContext, "Tag: $which - State: $isChecked", Toast.LENGTH_SHORT).show()
                checkedTags[which] = isChecked
            }

            builder.setCancelable(false)
            builder.setTitle("List of Tags")

            builder.setPositiveButton("Save") { dialog, which ->
                listCheckedTags = checkedTags // Save to adapter

                // Delete all tag - note
                for (i in relationships) {
                    if (i.note == editTask.id) {
                        // Remove relationship from database relationship
                        deleteRelationship(i)
                    }
                }

                var tmp = ""
                for (i in 0 until tags.size) {
                    if (listCheckedTags[i]) {
                        tmp += listTagsName[i] + ", "
                    }
                }
                tv_uncategorized.text = tmp
                handleSaveRelationship()
            }

            builder.setNegativeButton("Cancel") { dialog, which ->

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

        setInitialView()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_details_task, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.action_save-> {
                updateTaskDao()
                val intent = Intent()
                intent.putExtra(TASK_ID_KEY, editTask.id!!)
                if (position != -1)
                    intent.putExtra(CODE_EDIT_TASK_POSITION, position)
                intent.putExtra(CODE_DELETE_TASK_POSITION, -1)
                setResult(Activity.RESULT_OK, intent)
                finish()
                return true
            }
            R.id.action_delete-> {
                val builder = AlertDialog.Builder(this@DetailsTaskActivity)
                builder.setTitle("Delete Confirmation")
                    .setMessage("Are you sure to remove this task?")
                    .setPositiveButton("OK") { _, _ ->
                        removeTaskDao()
                        val intent = Intent()
                        intent.putExtra(TASK_ID_KEY, editTask.id!!)
                        if (position != -1)
                            intent.putExtra(CODE_DELETE_TASK_POSITION, position)
                        intent.putExtra(CODE_EDIT_TASK_POSITION, -1)
                        setResult(Activity.RESULT_OK, intent)
                        finish()
                    }
                    .setNegativeButton(
                        "Cancel"
                    ) { dialog, _ -> dialog?.dismiss() }

                val myDialog = builder.create()
                myDialog.show()
                return true
            }
            else -> super.onOptionsItemSelected(item)
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

    private fun handleSaveRelationship() {
        val tagDao = dbTags.tagDAO()
        for (i in 0 until tags.size) {
            if (listCheckedTags[i]) {
                val tag = tagDao.findByTag(listTagsName[i])
                if (tag.id != null) {
                    val rel = Relationship(null, tag.id!!, editTask.id!!)
                    addNewRelationship(rel)
                    Toast.makeText(applicationContext, "Save Tag added: ${tag.id} - ${editTask.id}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun addNewRelationship(newRelationship: Relationship) {
        val relationshipDao = dbRelationships.relationshipDAO()
        val id = relationshipDao.insert(newRelationship)
        newRelationship.id = id.toInt()
    }

    private fun getTagName(tagId: Int): String {
        val tagDao = dbTags.tagDAO()
        return tagDao.findById(tagId).tag
    }

    private fun getDataInTaskActivity() {
        editTask.title = edt_title.text.toString()
        editTask.description = edt_description_note.text.toString()
        editTask.checked = cb_completed.isChecked

        editTask.priority = indexRadioButton
        editTask.categorize = tv_uncategorized.text.toString()
    }

    private fun addTagsAdapter(tag: Tag) {
        this.tags.add(tag)
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

    private fun deleteRelationship(relationship: Relationship) {
        val relationshipDao = dbRelationships.relationshipDAO()
        relationshipDao.delete(relationship)
    }

    private fun updateTaskDao() {
        getDataInTaskActivity()

        taskDao.update(editTask)
    }

    private fun removeTaskDao() {
        taskDao.delete(editTask)
    }

    private fun setInitialView() {
        edt_title.setText(editTask.title)
        edt_description_note.setText(editTask.description)
        cb_completed.isChecked = editTask.checked
        tv_uncategorized.text = editTask.categorize

        val btnLow = radio_priority_choice.findViewById<RadioButton>(R.id.btnLow)
        val btnNormal = radio_priority_choice.findViewById<RadioButton>(R.id.btnNormal)
        val btnHigh = radio_priority_choice.findViewById<RadioButton>(R.id.btnHigh)

        when (editTask.priority) {
            0 -> btnLow.isChecked = true
            1 -> btnNormal.isChecked = true
            2 -> btnHigh.isChecked = true
        }
    }
}
