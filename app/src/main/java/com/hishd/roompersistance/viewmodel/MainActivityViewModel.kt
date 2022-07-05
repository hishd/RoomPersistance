package com.hishd.roompersistance.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hishd.roompersistance.persistence.Subscriber
import com.hishd.roompersistance.persistence.SubscriberRepository
import kotlinx.coroutines.launch

class MainActivityViewModel(val subscriberRepo: SubscriberRepository) : ViewModel() {
    val subscriberName = MutableLiveData<String>()
    val subscriberEmail = MutableLiveData<String>()

    val btnSaveUpdate = MutableLiveData<String>()
    val btnClearDelete = MutableLiveData<String>()

    val subscribersList = subscriberRepo.subscribers

    init {
        btnSaveUpdate.value = "Save"
        btnClearDelete.value = "Clear All"
    }

    fun addSubscriber(subscriber: Subscriber) = viewModelScope.launch {
        subscriberRepo.addSubscriber(subscriber)
    }

    fun updateSubscriber(subscriber: Subscriber) = viewModelScope.launch {
        subscriberRepo.updateSubscriber(subscriber)
    }

    fun deleteSubscriber(subscriber: Subscriber) = viewModelScope.launch {
        subscriberRepo.deleteSubscriber(subscriber)
    }

    fun clearAllSubscribers() = viewModelScope.launch {
        subscriberRepo.deleteAllSubscriberData()
    }

    fun saveOrUpdate() {
        addSubscriber(
            Subscriber(
                id = 0,
                name = subscriberName.value ?: "",
                email = subscriberEmail.value ?: ""
            )
        )

        subscriberName.value = ""
        subscriberEmail.value = ""
    }

    fun clearOrDelete() {
        clearAllSubscribers()
    }
}