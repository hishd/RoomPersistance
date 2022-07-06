package com.hishd.roompersistance.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hishd.roompersistance.persistence.Subscriber
import com.hishd.roompersistance.persistence.SubscriberRepository
import com.hishd.roompersistance.util.Event
import kotlinx.coroutines.launch

class MainActivityViewModel(val subscriberRepo: SubscriberRepository) : ViewModel() {
    val subscriberName = MutableLiveData<String>()
    val subscriberEmail = MutableLiveData<String>()

    val btnSaveUpdate = MutableLiveData<String>()
    val btnClearDelete = MutableLiveData<String>()

    val subscribersList = subscriberRepo.subscribers

    private var selectedSubscriber: Subscriber? = null

    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>> get() = statusMessage

    init {
        btnSaveUpdate.value = "Save"
        btnClearDelete.value = "Clear All"
    }

    private fun addSubscriber(subscriber: Subscriber) = viewModelScope.launch {
        subscriberRepo.addSubscriber(subscriber)
        statusMessage.value = Event("Subscriber Added Successfully")
    }

    private fun updateSubscriber(subscriber: Subscriber) = viewModelScope.launch {
        subscriberRepo.updateSubscriber(subscriber)
        statusMessage.value = Event("Subscriber Udpated Successfully")
    }

    private fun deleteSubscriber(subscriber: Subscriber) = viewModelScope.launch {
        subscriberRepo.deleteSubscriber(subscriber)
        statusMessage.value = Event("Subscriber Deleted Successfully")
    }

    private fun clearAllSubscribers() = viewModelScope.launch {
        subscriberRepo.deleteAllSubscriberData()
        statusMessage.value = Event("Subscribers Cleared Successfully")
    }

    fun saveOrUpdate() {
        if(this.selectedSubscriber!= null) {
            selectedSubscriber!!.name = subscriberName.value?: ""
            selectedSubscriber!!.email = subscriberEmail.value?: ""
            updateSubscriber(selectedSubscriber!!)
            selectedSubscriber = null
        } else {
            addSubscriber(
                Subscriber(
                    id = 0,
                    name = subscriberName.value ?: "",
                    email = subscriberEmail.value ?: ""
                )
            )
        }
        resetValues()
        setButtonText(MainActivityOperation.SaveClear)
    }

    fun clearOrDelete() {
        if(this.selectedSubscriber!= null) {
            deleteSubscriber(selectedSubscriber!!)
            selectedSubscriber = null
        } else {
            clearAllSubscribers()
        }
        resetValues()
        setButtonText(MainActivityOperation.SaveClear)
    }

    fun initUpdateDelete(subscriber: Subscriber) {
        subscriberName.value = subscriber.name
        subscriberEmail.value = subscriber.email
        setButtonText(MainActivityOperation.UpdateDelete)
        this.selectedSubscriber = subscriber
    }

    private fun setButtonText(operation: MainActivityOperation) {
        when(operation) {
            MainActivityOperation.SaveClear -> {
                btnSaveUpdate.value = "Save"
                btnClearDelete.value = "Clear All"
            }
            MainActivityOperation.UpdateDelete -> {
                btnSaveUpdate.value = "Update"
                btnClearDelete.value = "Delete"
            }
        }
    }

    private fun resetValues() {
        subscriberName.value = ""
        subscriberEmail.value = ""
    }
}

enum class MainActivityOperation {
    SaveClear,
    UpdateDelete
}