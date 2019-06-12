package group14.finalproject.mytodotask.model

import group14.finalproject.mytodotask.room.*

class TaskRepo (
    private val taskDao: TaskDAO,
    private val tagDao: TagDAO,
    private val relationshipDao: RelationshipDAO
) {
    private lateinit var arrayListTasks: ArrayList<Task>
    private lateinit var arrayListTags: ArrayList<Tag>
    private lateinit var arrayListRelationships: ArrayList<Relationship>

    fun setArrayListTasks() {
        this.arrayListTasks = convertListToArrayListTask(taskDao.getAll())
    }

    fun getArrayListTasks(): ArrayList<Task> {
        return arrayListTasks
    }

    fun setArrayListTags() {
        this.arrayListTags = convertListToArrayListTag(tagDao.getAll())
    }

    fun getArrayListTags(): ArrayList<Tag> {
        return arrayListTags
    }

    fun setArrayListRelationships() {
        this.arrayListRelationships = convertListToArrayListRelationship(relationshipDao.getAll())
    }

    fun getArrayListRelationships(): ArrayList<Relationship> {
        return arrayListRelationships
    }

    fun convertListToArrayListTask(list: List<Task>): ArrayList<Task> {
        val tmp = ArrayList<Task>()
        for (i in list) {
            tmp.add(i)
        }
        return tmp
    }

    fun convertListToArrayListTag(list: List<Tag>): ArrayList<Tag> {
        val tmp = ArrayList<Tag>()
        for (i in list) {
            tmp.add(i)
        }
        return tmp
    }

    fun convertListToArrayListRelationship(list: List<Relationship>): ArrayList<Relationship> {
        val tmp = ArrayList<Relationship>()
        for (i in list) {
            tmp.add(i)
        }
        return tmp
    }
}