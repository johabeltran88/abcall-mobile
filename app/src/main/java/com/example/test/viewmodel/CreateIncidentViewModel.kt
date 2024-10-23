package com.example.test.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.test.model.Incident
import com.example.test.repository.CreateRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateIncidentViewModel(application: Application) : AndroidViewModel(application) {
    private val createRepository = CreateRepository(application)

    var subject = MutableLiveData<String>()
    var errorSubject = MutableLiveData<String>()
    var isValidSubject = MutableLiveData<Boolean>()

    var description = MutableLiveData<String>()
    var errorDescription = MutableLiveData<String>()
    var isValidDescription = MutableLiveData<Boolean>()

    var company = MutableLiveData<String>()
    var errorCompany = MutableLiveData<String>()
    var isValidCompany = MutableLiveData<Boolean>()

    var error = MutableLiveData<Boolean>()

    fun Create() {
        if (isValidSubject.value == true
            && isValidDescription.value == true
            && isValidCompany.value == true
        ) {
            val incident = Incident(subject.value, company.value, description.value)
            viewModelScope.launch(Dispatchers.Default) {
                try {
                    withContext(Dispatchers.IO) {
                        val token = createRepository.create(incident)
                        error.postValue(false)
                    }
                } catch (exception: Exception) {
                    error.postValue(true)
                }
            }
        }
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CreateIncidentViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CreateIncidentViewModel(application) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}