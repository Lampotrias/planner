package com.lamp.planner.presentation.features.repeatdialog

import android.os.Bundle
import android.util.SizeF
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import by.kirich1409.viewbindingdelegate.viewBinding
import com.lamp.planner.R
import com.lamp.planner.databinding.DialogRepeatBinding
import com.lamp.planner.domain.DayRepeat
import com.lamp.planner.domain.MonthRepeat
import com.lamp.planner.domain.None
import com.lamp.planner.domain.RepeatInterval
import com.lamp.planner.domain.WeekRepeat
import com.lamp.planner.domain.YearRepeat
import com.lamp.planner.extention.navigate
import com.lamp.planner.presentation.base.BaseDialog

class RepeatDialog : BaseDialog() {

    private val binding by viewBinding {
        DialogRepeatBinding.bind(it.requireView())
    }

    companion object {
        const val REPEAT_DIALOG_REQUEST_KEY = "REPEAT_DIALOG_REQUEST_KEY"
        const val REPEAT_DIALOG_REPEAT_OBJ = "REPEAT_DIALOG_REPEAT_OBJ"
    }

    override fun setSizeDialog() = SizeF(0.9f, 0f)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_repeat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.repeatNone.setOnClickListener { close(None) }
        binding.repeatDay.setOnClickListener { close(DayRepeat("111")) }
        binding.repeatWeek.setOnClickListener { close(WeekRepeat("test1123")) }
        binding.repeatMonth.setOnClickListener { close(MonthRepeat("teww@")) }
        binding.repeatYear.setOnClickListener { close(YearRepeat("sdf")) }

        binding.repeatDayParams.setOnClickListener { }
        binding.repeatWeekParams.setOnClickListener { }
        binding.repeatMonthParams.setOnClickListener { }
        binding.repeatYearParams.setOnClickListener { }
    }

    fun openParams(repeatInterval: RepeatInterval) {
        val navDirections =
            RepeatDialogDirections.actionRepeatDialogToRepeatParamsDialog(repeatInterval)
        navigate(navDirections)
    }

    fun close(repeatInterval: RepeatInterval) {
        setFragmentResult(
            REPEAT_DIALOG_REQUEST_KEY,
            bundleOf(REPEAT_DIALOG_REPEAT_OBJ to repeatInterval)
        )
        dialog?.dismiss()
    }
}
