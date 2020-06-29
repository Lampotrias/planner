package com.lamp.planner.presentation.features.myday

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentResultListener
import androidx.navigation.NavDirections
import androidx.recyclerview.widget.LinearLayoutManager
import com.lamp.planner.R
import com.lamp.planner.databinding.MyDayBinding
import com.lamp.planner.di.scope.PerFragment
import com.lamp.planner.domain.excetion.Failure
import com.lamp.planner.extention.navigate
import com.lamp.planner.presentation.DisplayableItem
import com.lamp.planner.presentation.adapters.CompositeAdapter
import com.lamp.planner.presentation.adapters.ManagerImpl
import com.lamp.planner.presentation.base.BaseFragment
import com.lamp.planner.presentation.features.eventdialog.EventDialog
import com.lamp.planner.presentation.features.myday.di.DaggerMyDayComponent
import moxy.ktx.moxyPresenter
import javax.inject.Inject

@PerFragment
class MyDayFragment : BaseFragment(),
    MyDayView {

    private lateinit var binding: MyDayBinding

    @Inject
    lateinit var presenterProvider: MyDayPresenter
    private val mPresenter by moxyPresenter { presenterProvider }

    override fun onCreate(savedInstanceState: Bundle?) {
        onInitDependencyInjection()
        super.onCreate(savedInstanceState)

        parentFragmentManager.setFragmentResultListener(
            EventDialog.EVENT_DIALOG_RESULT,
            this,
            FragmentResultListener { _: String, result: Bundle ->
                mPresenter.processSaveEvent(result[EventDialog.EVENT_DIALOG_PARAM_OBJ] as EventTransferObject)
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MyDayBinding.inflate(inflater, container, false)
        binding.fab.setOnClickListener { mPresenter.showPopupDialog() }
        binding.btnStart.setOnClickListener { mPresenter.btnStartClick() }
        binding.btnStop.setOnClickListener { mPresenter.btnStopClick() }
        return binding.root
    }

    override fun onInitDependencyInjection() {
        DaggerMyDayComponent.builder()
            .appComponent(appComponent)
            .build()
            .inject(this)
    }

    override fun showAnimationFab(offset: Float) {
        binding.fab.rotation = offset * 180
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
