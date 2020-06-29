package com.lamp.planner.presentation.features.grouplistdialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.lamp.planner.databinding.GroupListDialogBinding
import com.lamp.planner.domain.Group
import com.lamp.planner.domain.excetion.Failure
import com.lamp.planner.presentation.adapters.CompositeAdapter
import com.lamp.planner.presentation.adapters.CompositeAdapter.ClickItemInterface
import com.lamp.planner.presentation.adapters.ManagerImpl
import com.lamp.planner.presentation.base.BaseDialog
import com.lamp.planner.presentation.features.grouplistdialog.di.DaggerGroupListDialogComponent
import moxy.ktx.moxyPresenter
import timber.log.Timber
import javax.inject.Inject

class GroupListDialog @Inject constructor() : BaseDialog(),
    GroupListView {

    private lateinit var binding: GroupListDialogBinding
    private val args: GroupListDialogArgs by navArgs()

    companion object {
        const val GROUPS_DIALOG_RESULT = "GROUPS_DIALOG_RESULT"
        const val GROUPS_DIALOG_PARAM_OBJ = "GROUPS_DIALOG_PARAM_OBJ"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.setInputNavArgs(args)
    }

    @Inject
    lateinit var presenterProvider: GroupListPresenter
    private val mPresenter by moxyPresenter { presenterProvider }

    override fun onInitDependencyInjection() {
        DaggerGroupListDialogComponent.builder()
            .appComponent(appContext)
            .build().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = GroupListDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onStop() {
        Timber.e("onStop")
        super.onStop()
    }

    override fun onDestroyView() {
        Timber.e("onDestroyView")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Timber.e("onDestroy")
        super.onDestroy()
    }

    override fun close(group: Group) {
        setFragmentResult(
            GROUPS_DIALOG_RESULT,
            bundleOf(GROUPS_DIALOG_PARAM_OBJ to group)
        )
        this.dialog?.dismiss()
    }

    override fun showGroups(managerImpl: ManagerImpl<Group>, groups: List<Group>, position: Int) {
        val groupAdapter = CompositeAdapter(managerImpl, object : ClickItemInterface<Group> {
            override fun onClick(item: Group) {
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
