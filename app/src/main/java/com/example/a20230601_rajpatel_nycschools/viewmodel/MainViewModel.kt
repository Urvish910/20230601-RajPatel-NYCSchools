package com.example.a20230601_rajpatel_nycschools.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a20230601_rajpatel_nycschools.data.SatResponse
import com.example.a20230601_rajpatel_nycschools.data.SchoolResponse
import com.example.a20230601_rajpatel_nycschools.repository.SchoolRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel(val repository: SchoolRepository) : ViewModel() {

    val schoolData = MutableLiveData<SchoolResponse>()
    val schoolSatData = MutableLiveData<SatResponse>()
    val message = MutableLiveData<String>()
    val pbProgressBar = MutableLiveData<Boolean>()

    /* getSchool Vm will get the data for school list and we can
      observe this in out activity or fragment
      */
    fun getSchoolVM() {
        val coroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            message.postValue(throwable.message)
            pbProgressBar.postValue(false)
            return@CoroutineExceptionHandler
        }
        viewModelScope.launch {
            Dispatchers.IO + coroutineExceptionHandler
            pbProgressBar.postValue(true)
            val response = repository.getSchool()
            if (!response.isSuccessful) {
                message.postValue("Something went Wrong,Please try again later.")
                pbProgressBar.postValue(false)
                return@launch
            } else if (response.body() == null) {
                message.postValue("Data not found.")
                pbProgressBar.postValue(false)
                return@launch
            }
            schoolData.postValue(response.body())
            message.postValue("Success")
            pbProgressBar.postValue(false)
            return@launch
        }
    }

    /* getSchool Vm will get the data for school sat score and we can
     observe this in out activity or fragment
     */
    fun getSchoolSATvM(dbn: String = "02326") {
        val coroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            pbProgressBar.postValue(false)
            return@CoroutineExceptionHandler
        }
        viewModelScope.launch {
            Dispatchers.IO + coroutineExceptionHandler
            pbProgressBar.postValue(true)
            val response = repository.getSAT(dbn)
            if (!response.isSuccessful) {
                pbProgressBar.postValue(false)
                return@launch
            } else if (response.body() == null) {
                pbProgressBar.postValue(false)
                return@launch
            }
            delay(500)
            pbProgressBar.postValue(false)
            schoolSatData.postValue(response.body())

            return@launch
        }
    }
}