package com.example.socialize.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialize.entity.User
import com.example.socialize.repository.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DatabaseViewModel @Inject constructor(private val repository: DatabaseRepository) : ViewModel() {

    private val _data = MutableLiveData<List<User>>()
    val data: LiveData<List<User>> get() = _data

    fun insertMember(user: User) {
        viewModelScope.launch {
            repository.insertUser(user)
            fetchMembers()
        }
    }

    fun fetchMembers() {
        viewModelScope.launch {
            _data.value = repository.getAllUsersList()
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            repository.updateUser(user)
            fetchMembers()
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch {
            repository.deleteUser(user)
            fetchMembers()
        }
    }

    fun getUserById(id: String): User? {
        return _data.value?.find { it.id == id }
    }

    fun getUserByCredentials(username: String, password: String, onResult: (User?) -> Unit) {
        viewModelScope.launch {
            val user = repository.getUserByCredentials(username, password)
            onResult(user)
        }
    }
}
