package com.example.test.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.test.common.getSession
import com.example.test.model.Incident
import com.example.test.repository.ConsumerRepository
import com.example.test.repository.IncidentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateIncidentViewModel(application: Application) : AndroidViewModel(application) {
    private val incidentRepository = IncidentRepository(application)
    private val consumerRepository = ConsumerRepository(application)

    var subject = MutableLiveData<String>()
    var errorSubject = MutableLiveData<String>()
    var isValidSubject = MutableLiveData<Boolean>()

    var description = MutableLiveData<String>()
    var errorDescription = MutableLiveData<String>()
    var isValidDescription = MutableLiveData<Boolean>()

    private val _companies = MutableLiveData<List<String>>()
    val companies: LiveData<List<String>> get() = _companies
    val selectedCompany = MutableLiveData<String>()
    var errorCompany = MutableLiveData<String>()
    var isValidCompany = MutableLiveData<Boolean>()

    var error = MutableLiveData<Boolean>()

    fun Create() {
        if (isValidSubject.value == true
            && isValidDescription.value == true
            && isValidCompany.value == true
        ) {
            val incident = Incident(subject.value, selectedCompany.value, description.value)
            viewModelScope.launch(Dispatchers.Default) {
                try {
                    //Esto es lo que en teoría llena la lista desplegable
                    viewModelScope.launch {
                        val items = consumerRepository.get(getSession(getApplication(), "Token"))
                        val options: List<String> = items.companies!!.map { it.name }
                        _companies.postValue(options)
                    }
                    withContext(Dispatchers.IO) {
                        val token = incidentRepository.create(incident)
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