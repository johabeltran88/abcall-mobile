package com.example.test.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.test.model.Notification
import com.example.test.model.Pcc
import com.example.test.repository.NotificationRepository
import com.example.test.repository.PccRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListNotificationViewModel(application: Application, private val pccId: String) : AndroidViewModel(application) {
    private val notificationRepository = NotificationRepository(application)

    val notification = MutableLiveData<List<Notification>>()
    val sdate = MutableLiveData<String>()
    val status = MutableLiveData<String>()
    val description = MutableLiveData<String>()
    val error = MutableLiveData<String>()

    // Function to fetch all pcc and post the value to the LiveData.
    fun fetchAllNotification(token: String) {
        try {
            viewModelScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.IO) {
                    notification.postValue(notificationRepository.getNotificationAll(token, pccId))
                }
            }
        } catch (exception: Exception) {
            error.postValue("Failed to fetch pcc: ${exception.message}")
        }
    }

    class Factory(private val application: Application, private val pccId: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ListNotificationViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ListNotificationViewModel(application, pccId) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}