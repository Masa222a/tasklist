package com.android.example.tasklist.View

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.example.tasklist.Model.Task
import com.android.example.tasklist.R

class TaskListAdapter(private var taskList: MutableList<Task>)
    : RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {
    private lateinit var listener: OnItemClickListener

    interface OnItemClickListener{
        fun onItemClickListener(view: View, position: Int, clickedText: Task)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var label: TextView = itemView.findViewById(R.id.task_Label)
        var date: TextView = itemView.findViewById(R.id.date)
        var favorite: ImageView = itemView.findViewById(R.id.add_Favorite)
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
            holder.favorite.setImageResource(R.drawable.ic_baseline_favorite_50_red)
        } else {
            holder.favorite.setImageResource(R.drawable.ic_baseline_favorite_50_gray)
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
        this.taskList = taskList
        notifyDataSetChanged()
    }
}
