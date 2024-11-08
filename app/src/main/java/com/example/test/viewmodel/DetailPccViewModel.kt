package com.example.test.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.test.model.Pcc
import com.example.test.repository.PccRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailPccViewModel(application: Application, private val pccId: String) : AndroidViewModel(application) {
    private val pccRepository = PccRepository(application)

    val subject = MutableLiveData<String>()
    val company = MutableLiveData<String>()
    val description = MutableLiveData<String>()
    val pcc = MutableLiveData<Pcc?>()
    val error = MutableLiveData<String>()

    fun fetchAllPcc(token: String) {
        try {
            viewModelScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.IO) {
                    val pccList = pccRepository.getAll(token)
                    val foundPcc = pccList.find { it.id == pccId } // Filtramos el Pcc por el ID
                    if (foundPcc != null) {
                        pcc.postValue(foundPcc) // Ahora esto es válido porque Pcc? puede ser nulo
                    } else {
                        error.postValue("Pcc with id $pccId not found")
                    }
                }
            }
        } catch (exception: Exception) {
            error.postValue("Failed to fetch pcc: ${exception.message}")
        }
    }

    class Factory(private val application: Application, private val pccId: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailPccViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DetailPccViewModel(application, pccId) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}