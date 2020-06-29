package com.lamp.planner.presentation.features.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.lamp.planner.databinding.AuthFragmentBinding
import com.lamp.planner.domain.excetion.Failure
import com.lamp.planner.presentation.base.BaseFragment
import com.lamp.planner.presentation.features.auth.di.DaggerAuthComponent
import moxy.ktx.moxyPresenter
import javax.inject.Inject

class AuthFragment : BaseFragment(), AuthView {
    @Inject
    lateinit var presenterProvider: AuthPresenter
    private val mPresenter by moxyPresenter { presenterProvider }
    private lateinit var binding: AuthFragmentBinding
    private lateinit var gso: GoogleSignInOptions
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onInitDependencyInjection() {
        DaggerAuthComponent.builder().appComponent(appComponent).build().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AuthFragmentBinding.inflate(inflater, container, false)
        binding.singGoogleBtn.setOnClickListener {
            val startToResult =
                registerForActivityResult(
                    ActivityResultContracts.StartActivityForResult()
                ) {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                    mPresenter.handleSignInResult(task)
                }
            val signInIntent = mGoogleSignInClient.signInIntent
            startToResult.launch(signInIntent)
        }

        return binding.root
    }

    override fun closeAuthFragmentWithNotify(message: String) {
        notify("Добро пожаловавать $message!")
        findNavController().popBackStack()
    }

    override fun handleError(failure: Failure) {
        notify(prepareFailure(failure))
    }
}
