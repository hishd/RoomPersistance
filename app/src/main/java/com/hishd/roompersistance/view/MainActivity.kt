package com.hishd.roompersistance.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.hishd.roompersistance.R
import com.hishd.roompersistance.databinding.ActivityMainBinding
import com.hishd.roompersistance.persistence.SubscriberDB
import com.hishd.roompersistance.persistence.SubscriberRepository
import com.hishd.roompersistance.viewmodel.MainActivityViewModel
import com.hishd.roompersistance.viewmodel.MainActivityViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var viewModelFactory: MainActivityViewModelFactory
    private lateinit var subscriberRepository: SubscriberRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initResources()
        getSubscribersList()
    }

    private fun initResources() {
        val dao = SubscriberDB.getInstance(application).subscriberDAO
        subscriberRepository = SubscriberRepository(dao)
        viewModelFactory = MainActivityViewModelFactory(subscriberRepo = subscriberRepository)
        viewModel = ViewModelProvider(this, factory = viewModelFactory)[MainActivityViewModel::class.java]

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
    }

    private fun getSubscribersList() {
        lifecycleScope.launch {
            viewModel.subscribersList.collect {
                Log.d("List", it.toString())
            }
        }
    }
}