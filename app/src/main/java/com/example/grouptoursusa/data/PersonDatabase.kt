package com.example.grouptoursusa.data

import android.content.Context
import androidx.room.*
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized
import java.util.concurrent.locks.Lock

@Database(entities = [Person::class], version = 1, exportSchema = false)
abstract class PersonDatabase : RoomDatabase() {

    abstract fun personDao(): PersonDAO

    companion object {
        @Volatile
        private var INSTANCE: PersonDatabase? = null

        fun getDatabase(context: Context): PersonDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            val instance = Room.databaseBuilder(
                context.applicationContext,
                PersonDatabase::class.java,
                "person_database"
            ).build()
            INSTANCE = instance
            return instance
        }
    }
}