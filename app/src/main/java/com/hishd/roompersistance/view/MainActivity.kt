package com.hishd.roompersistance.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hishd.roompersistance.R
import com.hishd.roompersistance.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}