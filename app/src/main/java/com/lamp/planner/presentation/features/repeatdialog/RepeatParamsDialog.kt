package com.lamp.planner.presentation.features.repeatdialog

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.SizeF
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.lamp.planner.R
import com.lamp.planner.databinding.DialogRepeatParamBinding
import com.lamp.planner.domain.RepeatInterval
import com.lamp.planner.extention.activate
import com.lamp.planner.extention.hide
import com.lamp.planner.extention.inActive
import com.lamp.planner.extention.show
import com.lamp.planner.presentation.base.BaseDialog
import dagger.hilt.android.AndroidEntryPoint
import moxy.ktx.moxyPresenter
import timber.log.Timber
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class RepeatParamsDialog : BaseDialog(), RepeatParamView {
    @Inject
    lateinit var presenterProvider: RepeatParamsPresenter
    private val mPresenter by moxyPresenter { presenterProvider }
    private val args: RepeatParamsDialogArgs by navArgs()
    private val binding by viewBinding {
        DialogRepeatParamBinding.bind(it.requireView())
    }

    companion object {
        const val REPEAT_PARAMS_DIALOG_REQUEST_KEY = "REPEAT_PARAMS_DIALOG_REQUEST_KEY"
        const val REPEAT_PARAMS_DIALOG_REPEAT_OBJ = "REPEAT_PARAMS_DIALOG_REPEAT_OBJ"
    }

    override fun setSizeDialog() = SizeF(0.9f, 0f)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.setInputNavArgs(args)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_repeat_param, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.alwaysMode.setOnClickListener { mPresenter.clickAlways() }
        binding.untilDateMode.setOnClickListener { mPresenter.clickUntilDate() }
        binding.untilCounterMode.setOnClickListener { mPresenter.clickUntilCounter() }
        initMainSeeker()
        initChildSeeker()
        binding.submitChildDialog.setOnClickListener { mPresenter.submitChildDialog(binding.countSeek.progress + 1) }
        binding.submitButton.setOnClickListener {
            mPresenter.clickSubmit(binding.repeatIntervalSeek.progress + 1)
        }
    }

    override fun openCalendar() {
        val cal = Calendar.getInstance()
        val datePicker = DatePickerDialog(requireContext(), { _, year, month, day ->
            mPresenter.clickOkCalendar(year, month, day)
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))
        datePicker.show()
    }

    override fun setAlways(status: String, periodName: String) {
        binding.alwaysMode.activate()
        binding.untilDateMode.inActive()
        binding.untilCounterMode.inActive()
        binding.status.text = status
        updateRepeatIntervalText(binding.repeatIntervalSeek.progress)
        binding.valueName.text = periodName
    }

    override fun setUntilDate(status: String, periodName: String) {
        binding.alwaysMode.inActive()
        binding.untilDateMode.activate()
        binding.untilCounterMode.inActive()
        binding.status.text = status
        updateRepeatIntervalText(binding.repeatIntervalSeek.progress)
        binding.valueName.text = periodName
    }

    override fun setCount(status: String, periodName: String) {
        binding.alwaysMode.inActive()
        binding.untilDateMode.inActive()
        binding.untilCounterMode.activate()
        binding.status.text = status
        updateRepeatIntervalText(binding.repeatIntervalSeek.progress)
        binding.valueName.text = periodName
    }

    private fun initMainSeeker() {
        binding.repeatIntervalSeek.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(view: SeekBar?, progress: Int, fromUser: Boolean) {
                Timber.tag("repeatIntervalSeek").e(progress.toString())
                updateRepeatIntervalText(progress)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })
    }

    private fun updateCountText(seekProgress: Int) {
        binding.valueCount.text = (seekProgress + 1).toString()
    }

    private fun updateRepeatIntervalText(seekProgress: Int) {
        binding.valueRepeatInterval.text = (seekProgress + 1).toString()
    }

    private fun initChildSeeker() {
        binding.countSeek.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(view: SeekBar?, progress: Int, fromUser: Boolean) {
                Timber.tag("countSeek").e(progress.toString())
                updateCountText(progress)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })
    }

    override fun showChildParamsDialog(setShow: Boolean, count: Int) {
        if (setShow) {
            binding.startContainer.hide()
            binding.ChildContainer.show()
            binding.countSeek.progress = (count - 1)
            updateCountText(binding.countSeek.progress)
        } else {
            binding.startContainer.show()
            binding.ChildContainer.hide()
        }
    }

    override fun close(repeatObject: RepeatInterval) {
        setFragmentResult(
            REPEAT_PARAMS_DIALOG_REQUEST_KEY,
            bundleOf(REPEAT_PARAMS_DIALOG_REPEAT_OBJ to repeatObject)
        )
        dialog?.dismiss()
    }

    override fun setRepeatInterval(repeatInterval: Int) {
        binding.repeatIntervalSeek.progress = repeatInterval - 1
        updateRepeatIntervalText(binding.repeatIntervalSeek.progress)
    }
}
