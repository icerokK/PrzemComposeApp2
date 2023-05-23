package com.junatrade.przemcomposeapp2

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.concurrent.fixedRateTimer

class MainViewModel(app: Application): AndroidViewModel(app) {
    private val repo = Repository(app.applicationContext)

    fun getContacts(): Flow<List<Contact>>{
        return repo.getAll()
    }

    fun deleteContact(contact: Contact) = CoroutineScope(viewModelScope.coroutineContext).launch{
        repo.delete(listOf(contact))
    }

    init {
        CoroutineScope(Dispatchers.IO).launch {
            repo.dropDatabase()
            populateDatabase()
        }
    }

    private fun populateDatabase(){
        repeat(100){
            val time = System.currentTimeMillis()
            val contact =
                Contact(
                    name = "${time % 100}",
                    surname = "${time % 100}",
                    number = "$time",
                    ice = (time % 2 == 0L)
                )
            CoroutineScope(viewModelScope.coroutineContext).launch {
                repo.insertAll(listOf(contact))
            }
        }
    }
}