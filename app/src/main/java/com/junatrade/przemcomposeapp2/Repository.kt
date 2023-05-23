package com.junatrade.przemcomposeapp2

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlin.random.Random

class Repository(context: Context): ContactDAO {
//    fun fetchData(): Int {
//        return Random.nextInt(0, 1000)
//    }

    private val dao = ContactDb.getInstance(context).contactDao()

    override suspend fun insertAll(contact: List<Contact>) = withContext(Dispatchers.IO) {
        dao.insertAll(contact)
    }

    override suspend fun delete(contact: List<Contact>) = withContext(Dispatchers.IO) {
        dao.delete(contact)
    }

    override suspend fun update(contact: Contact) = withContext(Dispatchers.IO) {
        dao.update(contact)
    }

    override fun getAll(): Flow<List<Contact>> {
        return dao.getAll()
    }

    override suspend fun dropDatabase() = withContext(Dispatchers.IO) {
        dao.dropDatabase()
    }
}