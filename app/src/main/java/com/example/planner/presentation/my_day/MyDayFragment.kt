package com.example.planner.presentation.my_day

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.planner.AndroidApp
import com.example.planner.databinding.MyDayBinding
import com.example.planner.di.scope.PerFragment
import com.example.planner.presentation.event_dialog.EventDialog
import com.example.planner.presentation.my_day.di.DaggerMyDayComponent
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
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

    override fun showAddEventPopupDialog() {

        var event = EventDialog()
        //ff.onCreateAnimation(R.anim.slide_from_right, true, R.anim.slide_to_right)
        event.show(childFragmentManager, "eventAdd")
    }
}