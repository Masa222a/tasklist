package com.android.example.tasklist.Controller.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.example.tasklist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}