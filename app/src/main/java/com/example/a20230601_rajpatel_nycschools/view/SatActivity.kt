package com.example.a20230601_rajpatel_nycschools.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.a20230601_rajpatel_nycschools.R
import com.example.a20230601_rajpatel_nycschools.databinding.ActivitySatBinding
import com.example.a20230601_rajpatel_nycschools.repository.SchoolRepository
import com.example.a20230601_rajpatel_nycschools.viewmodel.MainViewModel
import com.example.a20230601_rajpatel_nycschools.viewmodel.MainViewModelFactory

class SatActivity : AppCompatActivity() {
    private lateinit var viewModel:MainViewModel
    lateinit var binding: ActivitySatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sat)

        val repository = SchoolRepository()
        val factory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)


        // get the data from main avtivity for dbn click btn
        val dbn = intent.getStringExtra("dbn")

        //initializing the-observers to read and set he data using databinding
        intiObserver()

        // to call the functions and get the values from Api Service
        // and setting it to databasing variable
        funcitonCall(dbn)

    }

    @SuppressLint("SetTextI18n")
    private fun intiObserver() {
        viewModel.pbProgressBar.observe(this) {
            when (it) {
                true -> binding.pbSatl.visibility = View.VISIBLE      // start the progress bar
                else -> binding.pbSatl.visibility = View.GONE         // stop the progress bar
            }
        }
        viewModel.schoolSatData.observe(this){
            if(!it.isEmpty()){
                binding.data = it[0]
            }else{
                binding.tvSchoolName.text = "Sorry No Data is Available. Please try later"
            }
        }

    }

    private fun funcitonCall(dbn: String?) {
        if (dbn!=null){
            viewModel.getSchoolSATvM(dbn)
        }else
        {
            viewModel.getSchoolSATvM()
        }
    }
}