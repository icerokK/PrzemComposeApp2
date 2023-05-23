package com.junatrade.przemcomposeapp2

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDAO {

    @Insert
    suspend fun insertAll(contact: List<Contact>)

    @Delete
    suspend fun delete(contact: List<Contact>)

    @Update
    suspend fun update(contact: Contact)

    @Query("SELECT * FROM contact_table")
    fun getAll(): Flow<List<Contact>>

    @Query("DELETE FROM contact_table")
    suspend fun dropDatabase()
}