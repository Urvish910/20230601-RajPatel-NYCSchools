package com.example.a20230601_rajpatel_nycschools.repository

import com.example.a20230601_rajpatel_nycschools.model.remote.ApiService

class SchoolRepository {

    val apiService = ApiService.getApiInstance()

    suspend fun getSchool() = apiService.getSchoolData()
    suspend fun getSAT(dbn:String) = apiService.getSATschoolData(dbn)

}