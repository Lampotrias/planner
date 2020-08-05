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
import com.lamp.planner.databinding.DialogRepeatParamBinding
import com.lamp.planner.domain.RepeatInterval
import com.lamp.planner.presentation.base.BaseDialog

class RepeatParamsDialog : BaseDialog() {
    private val binding by viewBinding {
        DialogRepeatParamBinding.bind(it.requireView())
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
        return inflater.inflate(R.layout.dialog_repeat_param, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // binding.
    }

    fun close(repeatInterval: RepeatInterval) {
        setFragmentResult(
            REPEAT_DIALOG_REQUEST_KEY,
            bundleOf(REPEAT_DIALOG_REPEAT_OBJ to repeatInterval)
        )
        dialog?.dismiss()
    }
}
