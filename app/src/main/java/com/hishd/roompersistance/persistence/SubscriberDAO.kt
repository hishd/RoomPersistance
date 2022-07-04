package com.hishd.roompersistance.persistence

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SubscriberDAO {
    @Insert
    suspend fun addSubscriber(subscriber: Subscriber): Long

    @Update
    suspend fun updateSubscriber(subscriber: Subscriber)

    @Delete
    suspend fun deleteSubscriber(subscriber: Subscriber)

    @Query("DELETE FROM tbl_subscriber")
    suspend fun deleteAll()

    @Query("SELECT * FROM tbl_subscriber")
    fun getAllSubscribers(): Flow<List<Subscriber>>
}