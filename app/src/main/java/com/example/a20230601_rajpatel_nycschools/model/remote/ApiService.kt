package com.example.a20230601_rajpatel_nycschools.model.remote

import com.example.a20230601_rajpatel_nycschools.data.SatResponse
import com.example.a20230601_rajpatel_nycschools.data.SchoolResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(SchoolConstants.HIGH_SCHOOL)
    suspend fun getSchoolData(): Response<SchoolResponse>

    @GET(SchoolConstants.SAT_SCORE)
    suspend fun getSATschoolData(@Query("dbn") bdn: String = "02326"): Response<SatResponse>

    companion object{
        fun getApiInstance() = ApiClient.myRetrofit.create(ApiService::class.java)
    }
}