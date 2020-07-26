package com.lamp.planner.presentation.features.auth

import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.lamp.planner.R
import com.lamp.planner.databinding.FragmentAuthBinding
import com.lamp.planner.domain.excetion.Failure
import com.lamp.planner.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import moxy.ktx.moxyPresenter
import javax.inject.Inject

@AndroidEntryPoint
class AuthFragment : BaseFragment(R.layout.fragment_auth), AuthView {
    @Inject
    lateinit var presenterProvider: AuthPresenter
    private val mPresenter by moxyPresenter { presenterProvider }
    private val binding by viewBinding {
        FragmentAuthBinding.bind(it.requireView())
    }
    private lateinit var gso: GoogleSignInOptions
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

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
    }

    override fun closeAuthFragmentWithNotify(message: String) {
        notify("Добро пожаловавать $message!")
        findNavController().popBackStack()
    }

    override fun handleError(failure: Failure) {
        notify(prepareFailure(failure))
    }
}
