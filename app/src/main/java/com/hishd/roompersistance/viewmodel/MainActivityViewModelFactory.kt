package com.hishd.roompersistance.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hishd.roompersistance.persistence.SubscriberRepository
import java.lang.IllegalArgumentException

class MainActivityViewModelFactory(private val subscriberRepo: SubscriberRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainActivityViewModel::class.java))
            return MainActivityViewModel(subscriberRepo = subscriberRepo) as T

        throw IllegalArgumentException("Could not find a matching ViewModel")
    }
}