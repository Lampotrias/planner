package com.lamp.planner.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
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
            holder.apply {
                groupName = item.name
                groupLogo = item.picture
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
                    setImageResource(item.picture)
                    startAnimation(animationShow)
                }
            }
        }
    }

    override fun isForViewType(items: List<Group>, position: Int): Boolean {
        return true
    }

    inner class GroupHolder(private val binding: GroupListHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var groupName: String
            get() = binding.groupName.text.toString()
            set(value) {
                binding.groupName.text = value
            }

        var groupLogo: Int = 0
            set(value) {
                binding.groupLogo.setImageResource(value)
            }

        fun getAnimationView() = binding.groupLogo
    }
}
