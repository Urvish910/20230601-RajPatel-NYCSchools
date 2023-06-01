package com.example.a20230601_rajpatel_nycschools.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.a20230601_rajpatel_nycschools.repository.SchoolRepository

class MainViewModelFactory(val repository: SchoolRepository):ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}