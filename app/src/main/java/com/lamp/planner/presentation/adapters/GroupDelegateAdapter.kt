package com.lamp.planner.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.lamp.planner.R
import com.lamp.planner.databinding.GroupListHolderBinding
import com.lamp.planner.domain.Group

class GroupDelegateAdapter : DelegateAdapter<Group> {
    private lateinit var layoutInflater: LayoutInflater
    private lateinit var binding: GroupListHolderBinding

    companion object {
        const val PAYLOAD_ENABLE = 1
        const val PAYLOAD_DISABLE = 2
    }

    private val animationHide by lazy {
        AnimationUtils.loadAnimation(
            binding.root.context,
            android.R.anim.fade_out
        )
    }
    private val animationShow by lazy {
        AnimationUtils.loadAnimation(
            binding.root.context,
            android.R.anim.fade_in
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        layoutInflater =
            LayoutInflater.from(parent.context)
                ?: throw kotlin.NullPointerException("layoutInflater error")
        binding = GroupListHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GroupHolder(binding)
    }

    override fun onBindViewHolder(
        items: List<Group>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        isSelected: Boolean,
        payloads: MutableList<Any>
    ) {
        if (holder is GroupHolder) {
            val item = items[position]
            with(holder) {
                setGroupName(item.name)
                setGroupLogo(item.picture)
                setColorLogo(item.color)
            }

            if (payloads.contains(PAYLOAD_ENABLE)) {
                with(holder.getAnimationView()) {
                    startAnimation(animationHide)
                    setImageResource(R.drawable.ic_baseline_check_circle_24)
                    startAnimation(animationShow)
                }
            } else if (payloads.contains(PAYLOAD_DISABLE)) {
                with(holder.getAnimationView()) {
                    startAnimation(animationHide)
                    holder.setGroupLogo(item.picture)
                    startAnimation(animationShow)
                }
            }
        }
    }

    override fun isForViewType(items: List<Group>, position: Int) = true

    inner class GroupHolder(private val binding: GroupListHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val context: Context? by lazy { binding.root.context }
        fun getAnimationView() = binding.groupLogo

        fun setGroupName(name: String) {
            binding.groupName.text = name
        }

        fun setGroupLogo(picture: Int) {
            context?.let {
                val imagesRes = it.resources.obtainTypedArray(R.array.all_images)
                binding.groupLogo.setImageResource(imagesRes.getResourceId(picture, -1))
                imagesRes.recycle()
            }
        }

        fun setColorLogo(color: String) {
            context?.let {
                val imagesRes = it.resources.obtainTypedArray(R.array.all_colors)
                val colorRes =
                    ContextCompat.getColor(it, imagesRes.getResourceId(color.toInt(), -1))
                binding.groupLogo.setColorFilter(colorRes)
                binding.groupLogo.alpha = 0.5f
                imagesRes.recycle()
            }
        }
    }
}
