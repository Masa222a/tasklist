package com.android.example.tasklist.View

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.example.tasklist.R
import com.android.example.tasklist.Model.Task

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

//        isFavoriteによってimageの色を変更
        if (task.isFavorite) {
//            holder.favorite
        } else {
//            holder.favorite
        }

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
        val falseList = taskList.filter { !it.isFavorite }
        this.taskList = falseList as MutableList<Task>
        notifyDataSetChanged()
    }
}
