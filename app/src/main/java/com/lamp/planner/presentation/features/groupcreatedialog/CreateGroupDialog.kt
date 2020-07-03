package com.lamp.planner.presentation.features.groupcreatedialog

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.lamp.planner.R
import com.lamp.planner.databinding.GroupCreateDialogBinding
import com.lamp.planner.presentation.base.BaseDialog
import com.lamp.planner.presentation.features.groupcreatedialog.di.DaggerGroupCreateComponent
import moxy.ktx.moxyPresenter
import javax.inject.Inject

class CreateGroupDialog @Inject constructor() : BaseDialog(), GroupCreateView {

    private lateinit var binding: GroupCreateDialogBinding

    @Inject
    lateinit var presenterProvider: GroupCreatePresenter
    private val mPresenter by moxyPresenter { presenterProvider }

    companion object {
        const val GROUP_ADD_DIALOG_RESULT = "GROUP_ADD_DIALOG_RESULT"
        const val GROUP_ADD_DIALOG_PARAM_NAME = "GROUP_ADD_DIALOG_PARAM_NAME"
        const val GROUP_ADD_DIALOG_PARAM_LOGO = "GROUP_ADD_DIALOG_PARAM_LOGO"
    }

    override fun onInitDependencyInjection() {
        DaggerGroupCreateComponent.builder().appComponent(appComponent).build().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = GroupCreateDialogBinding.inflate(inflater, container, false)
        binding.apply {
            nameGroup.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {}
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    mPresenter.setTextInNameField(nameGroup.text.toString())
                }
            })
        }
        return binding.root
    }

    override fun close() {
        setFragmentResult(
            GROUP_ADD_DIALOG_RESULT,
            bundleOf(
                GROUP_ADD_DIALOG_PARAM_NAME to binding.nameGroup.text.toString(),
                GROUP_ADD_DIALOG_PARAM_LOGO to R.drawable.group_work
            )
        )
        this.dialog?.dismiss()
    }

    override fun setEnableSubmit(enable: Boolean) {
        binding.submitButton.apply {
            setOnClickListener { if (enable) mPresenter.actionSubmit() }
            alpha = if (enable) 1f else 0.2f
        }
    }
}
