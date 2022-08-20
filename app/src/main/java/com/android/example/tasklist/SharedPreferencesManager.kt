package com.android.example.tasklist

import android.content.Context
import com.android.example.tasklist.Model.Task
import com.google.gson.Gson

class SharedPreferencesManager private constructor(){
    companion object {
        var instance: SharedPreferencesManager = SharedPreferencesManager()
    }

    fun getCurrentTaskList(context: Context, key: String): String? {
        val pref = context.getSharedPreferences(key, Context.MODE_PRIVATE)
        val json = pref.getString("taskList", null)
        return json
    }

    fun saveTaskList(context: Context, key: String, taskList: MutableList<Task>) {
        val pref = context.getSharedPreferences(key, Context.MODE_PRIVATE)
        val sharedPrefEditor = pref.edit()
        val gson = Gson()
        sharedPrefEditor.putString("taskList", gson.toJson(taskList))
        sharedPrefEditor.apply()
    }
}