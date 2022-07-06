package com.hishd.roompersistance.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.hishd.roompersistance.adapter.MainActivitySubscriberListAdapter
import com.hishd.roompersistance.databinding.ActivityMainBinding
import com.hishd.roompersistance.persistence.Subscriber
import com.hishd.roompersistance.persistence.SubscriberDB
import com.hishd.roompersistance.persistence.SubscriberRepository
import com.hishd.roompersistance.util.ItemOffsetDecoration
import com.hishd.roompersistance.viewmodel.MainActivityViewModel
import com.hishd.roompersistance.viewmodel.MainActivityViewModelFactory
import com.intuit.sdp.R.dimen
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var viewModelFactory: MainActivityViewModelFactory
    private lateinit var subscriberRepository: SubscriberRepository
    private lateinit var recyclerAdapter: MainActivitySubscriberListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initResources()
        setupRecyclerView()
        setupObservers()
    }

    private fun initResources() {
        val dao = SubscriberDB.getInstance(application).subscriberDAO
        subscriberRepository = SubscriberRepository(dao)
        viewModelFactory = MainActivityViewModelFactory(subscriberRepo = subscriberRepository)
        viewModel =
            ViewModelProvider(this, factory = viewModelFactory)[MainActivityViewModel::class.java]

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
    }

    private fun setupRecyclerView() {
        recyclerAdapter = MainActivitySubscriberListAdapter(callback = onSubscriberClicked)
        val decoration =
            ItemOffsetDecoration(this, dimen._2sdp, dimen._2sdp, dimen._5sdp, dimen._5sdp)
        with(binding) {
            recyclerSubscriber.layoutManager = LinearLayoutManager(this@MainActivity)
            recyclerSubscriber.addItemDecoration(decoration)
            recyclerSubscriber.adapter = recyclerAdapter
        }
        getSubscribersList()
    }

    private fun getSubscribersList() {
        lifecycleScope.launch {
            viewModel.subscribersList.collect {
                recyclerAdapter.setData(it)
            }
        }
    }

    private fun setupObservers() {
        viewModel.message.observe(this) {
            it.getContentIfNotHandled()?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private val onSubscriberClicked: (Subscriber) -> Unit = {
        viewModel.initUpdateDelete(it)
    }
}