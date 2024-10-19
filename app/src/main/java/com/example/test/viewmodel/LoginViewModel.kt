package com.example.test.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.test.model.Consumer
import com.example.test.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val authRepository = AuthRepository(application)

    var email = MutableLiveData<String>()
    var errorEmail = MutableLiveData<String>()
    var isValidEmail = MutableLiveData<Boolean>()

    var password = MutableLiveData<String>()
    var errorPassword = MutableLiveData<String>()
    var isValidPassword = MutableLiveData<Boolean>()

    var error = MutableLiveData<Boolean>()

    fun login() {
        if (isValidEmail.value == true
            && isValidPassword.value == true
        ) {
            val consumer = Consumer(email.value, password.value)
            viewModelScope.launch(Dispatchers.Default) {
                try {
                    withContext(Dispatchers.IO) {
                        val token = authRepository.login(consumer)
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
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LoginViewModel(application) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}