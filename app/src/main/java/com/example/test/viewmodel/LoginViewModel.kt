package com.example.test.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.test.common.SessionManager
import com.example.test.model.Login
import com.example.test.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(
    application: Application,
    sessionManager: SessionManager
) : AndroidViewModel(application) {
    private val authRepository = AuthRepository(application)

    var token = MutableLiveData<String>()

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
            val login = Login(email.value, password.value)
            viewModelScope.launch(Dispatchers.Default) {
                try {
                    withContext(Dispatchers.IO) {
                        token.postValue(authRepository.login(login))
                        error.postValue(false)
                    }
                } catch (exception: Exception) {
                    error.postValue(true)
                }
            }
        }
    }

    class Factory(
        private val application: Application,
        private val sessionManager: SessionManager
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LoginViewModel(application, sessionManager) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}