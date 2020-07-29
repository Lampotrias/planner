package com.lamp.planner.presentation.features.myday

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentResultListener
import androidx.navigation.NavDirections
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.lamp.planner.R
import com.lamp.planner.databinding.FragmentMyDayBinding
import com.lamp.planner.domain.EventTransferObject
import com.lamp.planner.domain.excetion.Failure
import com.lamp.planner.extention.navigate
import com.lamp.planner.presentation.DisplayableItem
import com.lamp.planner.presentation.adapters.CompositeAdapter
import com.lamp.planner.presentation.adapters.ManagerImpl
import com.lamp.planner.presentation.base.BaseFragment
import com.lamp.planner.presentation.features.eventdialog.EventDialog
import dagger.hilt.android.AndroidEntryPoint
import moxy.ktx.moxyPresenter
import javax.inject.Inject

@AndroidEntryPoint
class MyDayFragment : BaseFragment(R.layout.fragment_my_day), MyDayView {
    @Inject
    lateinit var presenterProvider: MyDayPresenter
    private val mPresenter by moxyPresenter { presenterProvider }
    private val binding by viewBinding {
        FragmentMyDayBinding.bind(it.requireView())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        parentFragmentManager.setFragmentResultListener(
            EventDialog.EVENT_DIALOG_RESULT,
            this,
            FragmentResultListener { _: String, result: Bundle ->
                mPresenter.processSaveEvent(result[EventDialog.EVENT_DIALOG_PARAM_OBJ] as EventTransferObject)
            })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fabMyDay.setOnClickListener { mPresenter.showPopupDialog() }
        binding.btnStart.setOnClickListener { mPresenter.btnStartClick() }
        binding.btnStop.setOnClickListener { mPresenter.btnStopClick() }
    }

    override fun showAnimationFab(offset: Float) {
        binding.fabMyDay.rotation = offset * 180
    }

    override fun showAddEventPopupDialog(navDirections: NavDirections) {
        this.navigate(navDirections)
    }

    override fun handleFailure(failure: Failure?) {
        notify(prepareFailure(failure))
    }

    override fun showEventList(
        managerImpl: ManagerImpl<DisplayableItem>,
        list: List<DisplayableItem>
    ) {
        val eventListAdapter = CompositeAdapter(managerImpl)
        eventListAdapter.setItemList(list)
        binding.listView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = eventListAdapter
        }
    }

    override fun showSuccessEventAddMessage() {
        Toast.makeText(
            requireContext(),
            getString(R.string.mess_add_success_event),
            Toast.LENGTH_SHORT
        ).show()
    }
}
