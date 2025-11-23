package com.example.socialize.viewmodel

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialize.repository.DatastoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DatastoreViewModel @Inject constructor(
    private val datastoreRepository: DatastoreRepository
) : ViewModel() {


    private fun getStringFlow(key: Preferences.Key<String>): Flow<String> {
        return datastoreRepository.getStringFlow(key)
    }

    private fun saveString(key: Preferences.Key<Any>, value: Any) {

        viewModelScope.launch {
//            datastoreRepository.saveString(key ,valuea)
        }
    }
}
