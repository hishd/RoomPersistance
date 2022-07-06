package com.hishd.roompersistance.persistence

class SubscriberRepository(private val dao: SubscriberDAO) {
    val subscribers = dao.getAllSubscribers()

    suspend fun addSubscriber(subscriber: Subscriber): Long {
        return dao.addSubscriber(subscriber)
    }

    suspend fun updateSubscriber(subscriber: Subscriber) : Int {
        return dao.updateSubscriber(subscriber)
    }

    suspend fun deleteSubscriber(subscriber: Subscriber) : Int {
        return dao.deleteSubscriber(subscriber)
    }

    suspend fun deleteAllSubscriberData() : Int {
        return dao.deleteAll()
    }
}