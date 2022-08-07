package com.android.example.tasklist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskListAdapter(private var taskList: MutableList<Task>)
    : RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {
    private lateinit var listener: OnItemClickListener

    interface OnItemClickListener{
        fun onItemClickListener(view: View, position: Int, clickedText: Task)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var label: TextView = itemView.findViewById(R.id.task_Label)
        var date: TextView = itemView.findViewById(R.id.date)
        var favorite: ImageButton = itemView.findViewById(R.id.add_Favorite)
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

        holder.favorite.setOnClickListener {
            listener.onItemClickListener(it, position, task)
        }
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        this.listener = listener
    }

    override fun getItemCount(): Int = taskList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateTaskList(taskList: MutableList<Task>) {
        this.taskList = taskList
        notifyDataSetChanged()
    }
}
