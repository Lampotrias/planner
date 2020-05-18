package com.example.planner.presentation.my_day

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentResultListener
import androidx.navigation.NavDirections
import com.example.planner.AndroidApp
import com.example.planner.R
import com.example.planner.databinding.MyDayBinding
import com.example.planner.di.scope.PerFragment
import com.example.planner.extention.navigate
import com.example.planner.presentation.event_dialog.EventDialog
import com.example.planner.presentation.my_day.di.DaggerMyDayComponent
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import timber.log.Timber
import javax.inject.Inject

@PerFragment
class MyDayFragment : MvpAppCompatFragment(), MyDayView {

    private lateinit var binding: MyDayBinding

    @Inject
    lateinit var presenterProvider: MyDayPresenter
    private val mPresenter by moxyPresenter { presenterProvider }

    override fun onCreate(savedInstanceState: Bundle?) {
        onInitDependencyInjection()
        super.onCreate(savedInstanceState)

        parentFragmentManager.setFragmentResultListener(EventDialog.EVENT_DIALOG_RESULT, this, FragmentResultListener{
                _: String, result: Bundle ->
            mPresenter.saveEvent(result[EventDialog.EVENT_DIALOG_PARAM_OBJ] as EventTransferObject)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MyDayBinding.inflate(inflater, container, false)
        binding.fab.setOnClickListener{mPresenter.showPopupDialog()}

        return binding.root
    }

    private fun onInitDependencyInjection() {
        DaggerMyDayComponent.builder()
            .appComponent((requireContext().applicationContext as AndroidApp).getComponent())
            .build()
            .inject(this)
    }

    override fun showAnimationFab(offset: Float) {
        binding.fab.rotation = offset * 180
    }

    override fun showAddEventPopupDialog(navDirections: NavDirections) {
//        val event = EventDialog()
//        childFragmentManager.setFragmentResult(EventDialog.EVENT_DIALOG_REQUEST, bundleOf(EventDialog.EVENT_DIALOG_PARAM_OBJ to "EVENT_DIALOG_REQUEST"))
//        event.show(childFragmentManager, "eventAdd")
        this.navigate(navDirections)
    }
}