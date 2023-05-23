package com.junatrade.przemcomposeapp2

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Contact::class], version = 1)
abstract class ContactDatabase: RoomDatabase() {
    abstract fun contactDao(): ContactDAO
}

object ContactDb{
    private var db: ContactDatabase? = null

    fun getInstance(context: Context): ContactDatabase {
        if(db == null){
            db = Room.databaseBuilder(context,
                ContactDatabase::class.java,
                "contact-database")
                .build()
        }
        return db!!
    }
}