package com.lamp.planner.presentation.features.grouplistdialog

import android.os.Bundle
import android.util.SizeF
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.lamp.planner.R
import com.lamp.planner.databinding.DialogGroupListBinding
import com.lamp.planner.domain.Group
import com.lamp.planner.domain.excetion.Failure
import com.lamp.planner.presentation.adapters.CompositeAdapter
import com.lamp.planner.presentation.adapters.CompositeAdapter.ClickItemInterface
import com.lamp.planner.presentation.adapters.ManagerImpl
import com.lamp.planner.presentation.base.BaseDialog
import dagger.hilt.android.AndroidEntryPoint
import moxy.ktx.moxyPresenter
import javax.inject.Inject

@AndroidEntryPoint
class GroupListDialog @Inject constructor() : BaseDialog(),
    GroupListView {
    private val binding by viewBinding {
        DialogGroupListBinding.bind(it.requireView())
    }
    private val args: GroupListDialogArgs by navArgs()

    companion object {
        const val GROUPS_DIALOG_REQUEST_KEY = "GROUPS_DIALOG_RESULT"
        const val GROUPS_DIALOG_PARAM_OBJ = "GROUPS_DIALOG_PARAM_OBJ"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.setInputNavArgs(args)
    }

    override fun setSizeDialog(): SizeF = SizeF(0.9f, 0f)

    @Inject
    lateinit var presenterProvider: GroupListPresenter
    private val mPresenter by moxyPresenter { presenterProvider }

    override fun close(group: Group) {
        setFragmentResult(
            GROUPS_DIALOG_REQUEST_KEY,
            bundleOf(GROUPS_DIALOG_PARAM_OBJ to group)
        )
        this.dialog?.dismiss()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_group_list, container, false)
    }

    override fun showGroups(managerImpl: ManagerImpl<Group>, groups: List<Group>, position: Int) {
        val groupAdapter = CompositeAdapter(managerImpl, object : ClickItemInterface<Group> {
            override fun onClick(item: Group, position: Int) {
                mPresenter.clickOnGroup(item)
            }
        })
        groupAdapter.setItemListWithDefault(groups, position)
        binding.groupListDialog.apply {
            adapter = groupAdapter
            layoutManager = GridLayoutManager(requireContext(), 5)
        }
    }

    override fun handleFailure(failure: Failure?) {
        notify(prepareFailure(failure))
    }
}
