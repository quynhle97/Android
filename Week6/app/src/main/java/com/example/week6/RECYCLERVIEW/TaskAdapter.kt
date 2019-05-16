package com.example.week6.RECYCLERVIEW

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.week6.R
import com.example.week6.ROOM.Task
import kotlinx.android.synthetic.main.item_task.view.*
import java.util.*

/**
 * Created by Huu Hoang on 4/17/19.
 */
class TaskAdapter(var items: ArrayList<Task>, val context: Context) : RecyclerView.Adapter<TaskViewHolder>() {

    lateinit var mListener: TaskItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): TaskViewHolder {
        return TaskViewHolder(LayoutInflater.from(context).inflate(R.layout.item_task, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(taskViewHolder: TaskViewHolder, position: Int) {
        taskViewHolder.tvDescription.text = "${items[position].description}"
        taskViewHolder.tvUserId.text = "Asigned to: " + items[position].userid

        taskViewHolder.itemView.setOnClickListener {
            mListener.onItemCLicked(position)
        }

        taskViewHolder.itemView.setOnLongClickListener {
            mListener.onItemLongCLicked(position)
            true
        }
    }

    fun setListener(listener: TaskItemClickListener) {
        this.mListener = listener
    }

    fun setData(items: ArrayList<Task>) {
        this.items = items
    }

    fun appendData(newTaskAdded: Task) {
        this.items.add(newTaskAdded)
        notifyItemInserted(items.size - 1)
    }

}

class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var tvDescription = view.tvDescription
    var tvUserId = view.tvUserId
}
