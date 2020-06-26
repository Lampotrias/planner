package com.example.planner.presentation.features.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.planner.databinding.AuthFragmentBinding
import com.example.planner.presentation.base.BaseFragment
import com.example.planner.presentation.features.auth.di.DaggerAuthComponent
import moxy.ktx.moxyPresenter
import javax.inject.Inject

class AuthFragment : BaseFragment(), AuthView {
    @Inject
    lateinit var presenterProvider: AuthPresenter
    private val mPresenter by moxyPresenter { presenterProvider }
    private lateinit var binding: AuthFragmentBinding

    override fun onInitDependencyInjection() {
        DaggerAuthComponent.builder().appComponent(appComponent).build().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AuthFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }
}
