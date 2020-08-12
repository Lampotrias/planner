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
import dagger.hilt.android.AndroidEntryPoint
import moxy.ktx.moxyPresenter
import javax.inject.Inject

@AndroidEntryPoint
class RepeatDialog : BaseDialog(), RepeatView {
    @Inject
    lateinit var presenterProvider: RepeatPresenter
    private val mPresenter by moxyPresenter { presenterProvider }

    private val binding by viewBinding {
        DialogRepeatBinding.bind(it.requireView())
    }

    companion object {
        const val REPEAT_DIALOG_REQUEST_KEY = "REPEAT_DIALOG_REQUEST_KEY"
        const val REPEAT_DIALOG_REPEAT_OBJ = "REPEAT_DIALOG_REPEAT_OBJ"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentFragmentManager.setFragmentResultListener(
            RepeatParamsDialog.REPEAT_PARAMS_DIALOG_REQUEST_KEY,
            this,
            { _: String, result: Bundle ->
                val interval =
                    result.getSerializable(RepeatParamsDialog.REPEAT_PARAMS_DIALOG_REPEAT_OBJ) as? RepeatInterval
                interval?.let { close(it) }
            })
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
        binding.repeatDay.setOnClickListener { close(makeDay()) }
        binding.repeatWeek.setOnClickListener { close(makeWeek()) }
        binding.repeatMonth.setOnClickListener { close(makeMonth()) }
        binding.repeatYear.setOnClickListener { close(makeYear()) }

        binding.repeatDayParams.setOnClickListener { makeDay().also { openParams(it) } }
        binding.repeatWeekParams.setOnClickListener { makeWeek().also { openParams(it) } }
        binding.repeatMonthParams.setOnClickListener { makeMonth().also { openParams(it) } }
        binding.repeatYearParams.setOnClickListener { makeYear().also { openParams(it) } }
    }

    private fun openParams(repeatInterval: RepeatInterval) {
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
//        findNavController().popBackStack()
    }

    private fun makeDay() = DayRepeat()
    private fun makeWeek() = WeekRepeat()
    private fun makeMonth() = MonthRepeat()
    private fun makeYear() = YearRepeat()
}
