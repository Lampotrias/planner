package com.example.planner.presentation.my_day

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.planner.AndroidApp
import com.example.planner.R
import com.example.planner.databinding.MyDayBinding
import com.example.planner.presentation.base.BaseFragment
import com.example.planner.presentation.base.ViewModelFactory
import com.example.planner.presentation.di.scope.PerFragment
import com.example.planner.presentation.my_day.di.DaggerMyDayComponent
import com.google.android.material.bottomsheet.BottomSheetBehavior
import java.util.*
import javax.inject.Inject

@PerFragment
class MyDayFragment : BaseFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory<MayDayViewModel>

    private lateinit var viewModel: MayDayViewModel
    private lateinit var sheetBehavior: BottomSheetBehavior<LinearLayout>
    private lateinit var binding: MyDayBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this, viewModelFactory).get(MayDayViewModel::class.java)
        binding =
            DataBindingUtil.inflate(inflater, R.layout.my_day, container, false)
        initBottom()
        binding.viewModel = viewModel
        return binding.root
    }

    private fun initBottom() {
//        binding.bottomSheetInclude.view = this
//        binding.bottomSheetInclude.viewModel = viewModel
//
//        viewModel.dateField.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
//            binding.bottomSheetInclude.datePicker.text = it
//        })
//
//        viewModel.timeField.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
//            binding.bottomSheetInclude.timePicker.text = it
//        })
//
//        viewModel.error.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
//            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
//        })
//
//        sheetBehavior = BottomSheetBehavior.from(binding.bottomSheetInclude.bottomSheet)
//        sheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
//            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
//            override fun onStateChanged(bottomSheet: View, newState: Int) {
////                when (newState) {
////                    BottomSheetBehavior.STATE_HIDDEN -> {
////                    }
////                    BottomSheetBehavior.STATE_EXPANDED -> binding.btBottomSheet.text =
////                        "Close Bottom Sheet"
////                    BottomSheetBehavior.STATE_COLLAPSED -> binding.btBottomSheet.text =
////                        "Expand Bottom Sheet"
////                    BottomSheetBehavior.STATE_DRAGGING -> {
////                    }
////                    BottomSheetBehavior.STATE_SETTLING -> {
////                    }
////                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {
////                    }
////                }
//            }
//        })
//
//        binding.fab.setOnClickListener { sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED }
    }

    fun showTimePicker() {
        val calendar = Calendar.getInstance()

        val tDialog = TimePickerDialog(
            activity,
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                //viewModel.setTime(hourOfDay, minute)
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        )
        tDialog.show()
    }

    fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val tDialog = DatePickerDialog(
            requireActivity(),
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                run {
                    //viewModel.setDate(year, month, dayOfMonth)
                    showTimePicker()
                }
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        tDialog.show()
    }

    /*private fun setUpToolbar(view: View) {
        //val toolbar: Toolbar = view.findViewById(R.id.app_bar)
        //val activity = activity as AppCompatActivity
        //activity.setSupportActionBar(toolbar)
        //val actionBar = activity.supportActionBar
        // actionBar?.title = "sdf"
    }*/

    override fun onInitDependencyInjection() {
        DaggerMyDayComponent.builder()
            .appComponent((requireContext().applicationContext as AndroidApp).getComponent())
            .build()
            .inject(this)
    }
}