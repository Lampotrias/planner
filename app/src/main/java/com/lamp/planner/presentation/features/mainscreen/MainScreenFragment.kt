package com.lamp.planner.presentation.features.mainscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavDirections
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.lamp.planner.AndroidApp
import com.lamp.planner.R
import com.lamp.planner.databinding.MainScreenBinding
import com.lamp.planner.domain.Group
import com.lamp.planner.domain.excetion.Failure
import com.lamp.planner.extention.navigate
import com.lamp.planner.presentation.adapters.CompositeAdapter
import com.lamp.planner.presentation.adapters.ManagerImpl
import com.lamp.planner.presentation.base.BaseFragment
import com.lamp.planner.presentation.features.mainscreen.di.DaggerMainScreenComponent
import moxy.ktx.moxyPresenter
import javax.inject.Inject

open class MainScreenFragment : BaseFragment(),
    MainScreenView {

    @Inject
    lateinit var presenterProvider: MainScreenPresenter
    private val mPresenter by moxyPresenter { presenterProvider }

    override fun onCreate(savedInstanceState: Bundle?) {
        onInitDependencyInjection()
        super.onCreate(savedInstanceState)
    }

    private lateinit var binding: MainScreenBinding
    private lateinit var sheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun handleFailure(failure: Failure?) {
        prepareFailure(failure)
    }

    override fun navigateAuth(navDirections: NavDirections) {
        navigate(navDirections)
    }

    override fun showGroups(
        managerImpl: ManagerImpl<Group>,
        groups: List<Group>
    ) {
        val groupAdapter =
            CompositeAdapter(managerImpl, object : CompositeAdapter.ClickItemInterface<Group> {
                override fun onClick(item: Group) {
                    mPresenter.clickGroup(item)
                }

                override fun onLongClick(item: Group) {
                    mPresenter.clickLongGroup(item)
                }
            })
        groupAdapter.setItemList(groups)
        binding.groupList.apply {
            adapter = groupAdapter
            layoutManager = GridLayoutManager(requireContext(), 5)
        }
    }

    override fun initAuthorizeForm() {
        val account = GoogleSignIn.getLastSignedInAccount(requireContext())
        if (account != null) {
            mPresenter.setUserAccount(account)
            binding.accountName.setOnClickListener {
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build()
                val mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
                mGoogleSignInClient.signOut()
                drawAuthFormForEveryOne()
            }
        } else {
            drawAuthFormForEveryOne()
        }
    }

    private fun drawAuthFormForEveryOne() {
        binding.accountName.apply {
            text = context.getString(R.string.log_in)
            setOnClickListener {
                mPresenter.clickAuthButton()
            }
        }
    }

    override fun setAccountCation(name: String) {
        binding.accountName.text = name
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MainScreenBinding.inflate(inflater, container, false)

        binding.btnMyDay.setOnClickListener { mPresenter.clickBtnMyDay() }
        binding.authButton.setOnClickListener { mPresenter.clickAuthButton() }
        initBottom()
        return binding.root
    }

    private fun initBottom() {
        sheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)
        sheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                mPresenter.setOffset(slideOffset)
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                mPresenter.setSlidingState(newState)
            }
        })
    }

    override fun showMessage(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun navigateMyDay(resId: Int) {
        navigate(binding.mContainer, resId)
        showBottomSheet()
    }

    private fun showBottomSheet() {
        sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun onInitDependencyInjection() {
        DaggerMainScreenComponent
            .builder()
            .appComponent((requireContext().applicationContext as AndroidApp).getComponent())
            .build().inject(this)
    }
}
