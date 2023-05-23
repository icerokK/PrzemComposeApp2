package com.junatrade.przemcomposeapp2

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact_table")
data class Contact(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    val name: String,
    val surname: String,
    val number: String,
    val ice: Boolean
    )
