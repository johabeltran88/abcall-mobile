package com.example.test.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.test.common.SessionManager
import com.example.test.model.Company
import com.example.test.model.Consumer
import com.example.test.model.Pcc
import com.example.test.repository.ConsumerRepository
import com.example.test.repository.PccRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreatePccViewModel(
    application: Application,
    sessionManager: SessionManager
) : AndroidViewModel(application) {
    private val consumerRepository = ConsumerRepository(application)
    private val pccRepository = PccRepository(application)

    var token = MutableLiveData<String>()
    var consumer = MutableLiveData<Consumer>()

    var company = MutableLiveData<Company>()
    var companyId = MutableLiveData<String>()
    var errorCompany = MutableLiveData<String>()
    var isValidCompany = MutableLiveData<Boolean>()

    var subject = MutableLiveData<String>()
    var errorSubject = MutableLiveData<String>()
    var isValidSubject = MutableLiveData<Boolean>()

    var description = MutableLiveData<String>()
    var errorDescription = MutableLiveData<String>()
    var isValidDescription = MutableLiveData<Boolean>()

    var errorGetConsumer = MutableLiveData<Boolean>()
    var errorCreatePcc = MutableLiveData<Boolean>()

    fun getConsumer(token: String) {
        this.token.value = token
        viewModelScope.launch(Dispatchers.Default) {
            try {
                withContext(Dispatchers.IO) {
                    consumer.postValue(consumerRepository.getConsumerByToken(token))
                    errorGetConsumer.postValue(false)
                }
            } catch (exception: Exception) {
                errorGetConsumer.postValue(true)
            }
        }
    }

    fun createPcc() {
        if (isValidSubject.value == true
            && isValidDescription.value == true
            && isValidCompany.value == true
        ) {
            val pcc = Pcc(subject.value, description.value)
            viewModelScope.launch(Dispatchers.Default) {
                try {
                    withContext(Dispatchers.IO) {
                        consumer.value?.id?.let {
                            companyId.value?.let { it1 ->
                                token.value?.let { it2 ->
                                    pccRepository.createPcc(
                                        it2, it1,
                                        it, pcc
                                    )
                                }
                            }
                        }
                        errorCreatePcc.postValue(false)
                    }
                } catch (exception: Exception) {
                    errorCreatePcc.postValue(true)
                }
            }
        }
    }

    class Factory(
        private val application: Application,
        private val sessionManager: SessionManager
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CreatePccViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CreatePccViewModel(application, sessionManager) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}