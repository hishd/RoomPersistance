package com.hishd.roompersistance.persistence

class SubscriberRepository(private val dao: SubscriberDAO) {
    val subscribers = dao.getAllSubscribers()

    suspend fun addSubscriber(subscriber: Subscriber) {
        dao.addSubscriber(subscriber)
    }

    suspend fun updateSubscriber(subscriber: Subscriber) {
        dao.updateSubscriber(subscriber)
    }

    suspend fun deleteSubscriber(subscriber: Subscriber) {
        dao.deleteSubscriber(subscriber)
    }

    suspend fun deleteAllSubscriberData() {
        dao.deleteAll()
    }
}