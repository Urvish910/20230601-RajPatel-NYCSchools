package com.example.a20230601_rajpatel_nycschools.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a20230601_rajpatel_nycschools.R
import com.example.a20230601_rajpatel_nycschools.adapter.RvSchoolAdapter
import com.example.a20230601_rajpatel_nycschools.databinding.ActivityMainBinding
import com.example.a20230601_rajpatel_nycschools.repository.SchoolRepository
import com.example.a20230601_rajpatel_nycschools.viewmodel.MainViewModel
import com.example.a20230601_rajpatel_nycschools.viewmodel.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    lateinit var adapter: RvSchoolAdapter
    private lateinit var viewModelFactory: MainViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // initialize view-model
        initViewModel()
        //making function call to get the data from repository
        initFunctionCall()
        //observe live data from view-model
        initObserver()
    }


    private fun initObserver() {
        // observing if the data is loading or has been loaded
        viewModel.pbProgressBar.observe(this) {
            when (it) {
                true -> binding.pbSchool.visibility = View.VISIBLE      // start the progress bar
                else -> binding.pbSchool.visibility = View.GONE         // stop the progress bar
            }
        }

        // Toasting the message for error or success to let the user know about the data processing
        viewModel.message.observe(this) {
            Toast.makeText(baseContext, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.schoolData.observe(this) {

           if(!it.isNullOrEmpty()){
                adapter = RvSchoolAdapter(it)
                binding.rvSchool.layoutManager = LinearLayoutManager(baseContext)
                binding.rvSchool.adapter = adapter

                btncall()                               // this function will check for button click on adapter

            } else{
               Toast.makeText(this,"No Internet", Toast.LENGTH_SHORT).show()
           }
        }
    }


    private fun btncall() {
        adapter.setOnBtnListner { schoolResponse, _ ->
            val intent = Intent(this,SatActivity::class.java)
            intent.putExtra("dbn",schoolResponse.dbn)
            startActivity(intent)
        }
    }

    private fun initFunctionCall() {
        viewModel.getSchoolVM()
    }

    private fun initViewModel() {
        val repository = SchoolRepository()
        viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory = viewModelFactory)[MainViewModel::class.java]
    }
}