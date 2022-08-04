package com.android.example.tasklist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskListAdapter(private var taskList: ArrayList<Task>)
    : RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {
    private lateinit var listener: OnTaskCellClickListener

    interface OnTaskCellClickListener {
        fun onItemClick(task: Task)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var label: TextView = itemView.findViewById(R.id.task_Label)
        var date: TextView = itemView.findViewById(R.id.date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.cell_task_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = taskList[position]

        holder.label.text = task.label
        holder.date.text = task.date
    }

    override fun getItemCount(): Int = taskList.size

    fun setOnTaskCellClickListener(listener: OnTaskCellClickListener) {
        this.listener = listener
    }

    fun updateTaskList(taskList: ArrayList<Task>) {
        this.taskList = taskList
        notifyDataSetChanged()
    }
}
