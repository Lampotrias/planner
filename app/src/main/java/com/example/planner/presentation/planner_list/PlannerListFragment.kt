package com.example.planner.presentation.planner_list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.planner.AndroidApp
import com.example.planner.R
import com.example.planner.presentation.planner_list.di.DaggerPlannerListComponent
import javax.inject.Inject

class PlannerListFragment: Fragment() {
    @Inject lateinit var mContext: Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootview = inflater.inflate(R.layout.planner_list_fragment, container, false)
        initInject()
        Toast.makeText(mContext, "5555", Toast.LENGTH_SHORT).show()
        return rootview
    }

    private fun initInject(){
        DaggerPlannerListComponent.builder().appComponent((requireContext().applicationContext as AndroidApp).getComponent()).build().inject(this)
    }
}