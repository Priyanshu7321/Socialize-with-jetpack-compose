package com.example.socialize.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.couchbase.lite.Document
import com.example.socialize.entity.User
import com.example.socialize.repository.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DatabaseViewModel @Inject constructor(private val repository: DatabaseRepository) : ViewModel() {

    private val _data = MutableLiveData<List<Map<String,Any?>>>()
    val data: LiveData<List<Map<String,Any?>>> get() = _data

    fun insertUser(user:User) {
        repository.insertData(user)
        fetchUsers()
    }

    fun fetchUsers() {
        _data.value = repository.queryData()
    }

    fun updateUser(id: String, map:Map<String,String>) {
        repository.updateData(id, map)
        fetchUsers()
    }

    fun deleteUser(id: String) {
        repository.deleteData(id)
        fetchUsers()
    }
}
