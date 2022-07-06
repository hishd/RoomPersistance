package com.hishd.roompersistance.viewmodel

import android.util.Patterns
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
        val rowID = subscriberRepo.addSubscriber(subscriber)
        if (rowID > -1)
            statusMessage.value = Event("Subscriber Added Successfully")
        else
            statusMessage.value = Event("Error Occurred")
    }

    private fun updateSubscriber(subscriber: Subscriber) = viewModelScope.launch {
        val rows = subscriberRepo.updateSubscriber(subscriber)
        if (rows > 0)
            statusMessage.value = Event("Subscriber Udpated Successfully")
        else
            statusMessage.value = Event("Error Occurred")
    }

    private fun deleteSubscriber(subscriber: Subscriber) = viewModelScope.launch {
        val rows = subscriberRepo.deleteSubscriber(subscriber)
        if (rows > 0)
            statusMessage.value = Event("Subscriber Deleted Successfully")
        else
            statusMessage.value = Event("Error Occurred")
    }

    private fun clearAllSubscribers() = viewModelScope.launch {
        val rows = subscriberRepo.deleteAllSubscriberData()
        if (rows > 0)
            statusMessage.value = Event("Subscribers Cleared Successfully")
        else
            statusMessage.value = Event("Error Occurred")
    }

    fun saveOrUpdate() {

        if(subscriberName.value == null || subscriberEmail.value == null) {
            statusMessage.value = Event("Subscriber Name & Email should be entered")
            return
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(subscriberEmail.value!!).matches()) {
            statusMessage.value = Event("Invalid Email")
            return
        }

        if (this.selectedSubscriber != null) {
            selectedSubscriber!!.name = subscriberName.value ?: ""
            selectedSubscriber!!.email = subscriberEmail.value ?: ""
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
        if (this.selectedSubscriber != null) {
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
        when (operation) {
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