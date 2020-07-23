package com.lamp.planner.presentation.features.groupcreatedialog

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.SizeF
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.navArgs
import com.lamp.planner.R
import com.lamp.planner.databinding.GroupCreateDialogBinding
import com.lamp.planner.domain.SimpleGroupFields
import com.lamp.planner.presentation.base.BaseDialog
import com.lamp.planner.presentation.features.groupcreatedialog.di.DaggerGroupCreateComponent
import moxy.ktx.moxyPresenter
import javax.inject.Inject

class CreateGroupDialog @Inject constructor() : BaseDialog(), GroupCreateView {

    private lateinit var binding: GroupCreateDialogBinding
    private val args: CreateGroupDialogArgs by navArgs()

    @Inject
    lateinit var presenterProvider: GroupCreatePresenter
    private val mPresenter by moxyPresenter { presenterProvider }

    companion object {
        const val GROUP_ADD_REQUEST_KEY = "GROUP_ADD_REQUEST_KEY"
        const val GROUP_FIELDS = "GROUP_ADD_DIALOG_RESULT_GROUP_FIELDS"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.setInputNavArgs(args)
    }

    override fun setSizeDialog(): SizeF = SizeF(0.9f, 0f)

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

    override fun close(simpleGroupFields: SimpleGroupFields) {
        setFragmentResult(
            GROUP_ADD_REQUEST_KEY,
            bundleOf(GROUP_FIELDS to simpleGroupFields)
        )
        this.dialog?.dismiss()
    }

    override fun setEnableSubmit(enable: Boolean) {
        binding.submitButton.apply {
            setOnClickListener { if (enable) mPresenter.actionSubmit() }
            alpha = if (enable) 1f else 0.2f
        }
    }

    override fun initDialog(name: String, image: Int, color: Int) {
        binding.nameGroup.setText(name)
        getImageByCode(image).also {
            binding.logoGroup.apply {
                setImageResource(it)
                setColorFilter(getColorImage(color))
                alpha = 0.5f
            }
        }
    }

    private fun getImageByCode(imageId: Int): Int {
        val imagesRes = resources.obtainTypedArray(R.array.all_images)
        val value = imagesRes.getResourceId(imageId, -1)
        imagesRes.recycle()
        return value
    }

    private fun getColorImage(color: Int): Int {
        val colorRes = resources.obtainTypedArray(R.array.all_colors)
        val value = ContextCompat.getColor(requireContext(), colorRes.getResourceId(color, -1))
        colorRes.recycle()
        return value
    }
}
