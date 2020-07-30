package com.lamp.planner.presentation.features.imagedialog

import android.os.Bundle
import android.util.SizeF
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.lamp.planner.R
import com.lamp.planner.databinding.DialogImageSelectorBinding
import com.lamp.planner.presentation.adapters.CompositeAdapter
import com.lamp.planner.presentation.adapters.ImageSelectorDelegateAdapter
import com.lamp.planner.presentation.adapters.ManagerImpl
import com.lamp.planner.presentation.base.BaseDialog

class ImageDialog : BaseDialog() {
    private val binding by viewBinding {
        DialogImageSelectorBinding.bind(it.requireView())
    }

    companion object {
        const val IMAGE_SELECTOR_REQUEST_KEY = "IMAGE_SELECTOR_REQUEST_KEY"
        const val IMAGE_SELECTOR_RESULT_IMAGE = "IMAGE_SELECTOR_RESULT_IMAGE"
    }

    private fun getImagesList(): List<Int> {
        val imagesRes = resources.obtainTypedArray(R.array.all_images)
        val end = imagesRes.length() - 1
        imagesRes.recycle()
        return (0..end).toList()
    }

    override fun setSizeDialog(): SizeF = SizeF(0f, 0.5f)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_image_selector, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageSelectorRecycler.apply {
            val manager = ManagerImpl<Int>().also { it.addDelegate(ImageSelectorDelegateAdapter()) }
            val imageSelectorAdapter =
                CompositeAdapter(manager, object : CompositeAdapter.ClickItemInterface<Int> {
                    override fun onClick(item: Int, position: Int) {
                        close(item)
                    }
                })
            imageSelectorAdapter.setItemList(getImagesList())
            adapter = imageSelectorAdapter
            layoutManager = GridLayoutManager(context, 5)
            addItemDecoration(ItemOffsetDecoration(20))
        }
    }

    fun close(image: Int) {
        setFragmentResult(
            IMAGE_SELECTOR_REQUEST_KEY,
            bundleOf(IMAGE_SELECTOR_RESULT_IMAGE to image)
        )
        this.dialog?.dismiss()
    }
}
