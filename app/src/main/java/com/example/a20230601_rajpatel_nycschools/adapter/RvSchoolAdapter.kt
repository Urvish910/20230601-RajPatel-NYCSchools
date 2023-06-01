package com.example.a20230601_rajpatel_nycschools.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.a20230601_rajpatel_nycschools.R
import com.example.a20230601_rajpatel_nycschools.data.SchoolResponse
import com.example.a20230601_rajpatel_nycschools.data.SchoolResponseItem
import com.example.a20230601_rajpatel_nycschools.databinding.SchoolViewHolderBinding

class RvSchoolAdapter(val list: SchoolResponse):RecyclerView.Adapter<RvSchoolAdapter.SchoolViewHolder>() {

    inner class SchoolViewHolder(val binding: SchoolViewHolderBinding):ViewHolder(binding.root){
        fun bind(schoolResponseItem: SchoolResponseItem) {
            binding.data = schoolResponseItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchoolViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding : SchoolViewHolderBinding = DataBindingUtil.inflate(layoutInflater, R.layout.school_view_holder,parent,false)
        return SchoolViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SchoolViewHolder, position: Int) {
        holder.bind(list[position])

        holder.binding.btnInfoSat.setOnClickListener {
            if (this::btnListner.isInitialized){
                btnListner(list[position],position)
            }
        }
    }

    lateinit var btnListner : (SchoolResponseItem,Int)->Unit

    fun setOnBtnListner(listner: (SchoolResponseItem,Int)->Unit){
        btnListner = listner
    }

}