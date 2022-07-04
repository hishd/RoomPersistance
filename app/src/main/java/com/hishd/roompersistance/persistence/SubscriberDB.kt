package com.hishd.roompersistance.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//Pass all the entities here
@Database(entities = [Subscriber::class], version = 1)
abstract class SubscriberDB: RoomDatabase() {
    abstract val subscriberDAO: SubscriberDAO

    //Creating a Singleton
    companion object {
        @Volatile
        private var INSTANCE: SubscriberDB? = null

        fun getInstance(context: Context): SubscriberDB {
            synchronized(this) {
                var instance: SubscriberDB? = INSTANCE
                if(instance==null) {
                    instance = Room.databaseBuilder(
                        context,
                        SubscriberDB::class.java,
                        "subscriber_db"
                    ).build()
                }
                return instance
            }
        }
    }
}