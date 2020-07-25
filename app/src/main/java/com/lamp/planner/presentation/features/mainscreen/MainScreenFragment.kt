package com.lamp.planner.presentation.features.mainscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentResultListener
import androidx.navigation.NavDirections
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.lamp.planner.R
import com.lamp.planner.databinding.MainScreenBinding
import com.lamp.planner.domain.Group
import com.lamp.planner.domain.SimpleGroupFields
import com.lamp.planner.domain.excetion.Failure
import com.lamp.planner.extention.navigate
import com.lamp.planner.presentation.adapters.CompositeAdapter
import com.lamp.planner.presentation.adapters.GroupDelegateAdapter
import com.lamp.planner.presentation.adapters.ManagerImpl
import com.lamp.planner.presentation.base.BaseFragment
import com.lamp.planner.presentation.features.groupcreatedialog.CreateGroupDialog
import com.lamp.planner.presentation.features.groupproperty.BottomSheetHelper
import com.lamp.planner.presentation.features.groupproperty.GroupPropertyBottom
import com.lamp.planner.presentation.features.imagedialog.ImageDialog
import com.lamp.planner.presentation.features.palettedialog.PaletteDialog
import dagger.hilt.android.AndroidEntryPoint
import moxy.ktx.moxyPresenter
import javax.inject.Inject

@AndroidEntryPoint
open class MainScreenFragment : BaseFragment(),
    MainScreenView {
    @Inject
    lateinit var presenterProvider: MainScreenPresenter
    private val mPresenter by moxyPresenter { presenterProvider }

    private val bottomCallback = object : GroupPropertyBottom.OnClickBottomButton {
        override fun clickPalette() {
            mPresenter.clickPalette()
        }

        override fun clickImage() {
            mPresenter.clickImage()
        }

        override fun clickEdit() {
            mPresenter.clickEditGroup()
        }

        override fun clickDelete() {
            mPresenter.clickDelete()
        }

        override fun clickBookmark() {
            mPresenter.clickSetDefault()
        }
    }
    private val manager by lazy {
        ManagerImpl<Group>().also { it.addDelegate(GroupDelegateAdapter()) }
    }
    private val groupAdapter by lazy {
        CompositeAdapter(manager, GroupClickListener())
    }
    private val groupPropertyHelper: GroupPropertyBottom by lazy {
        BottomSheetHelper(
            binding.propertyInclude,
            binding.groupPropertySheet
        ).also { it.setCallback(bottomCallback) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        parentFragmentManager.setFragmentResultListener(
            CreateGroupDialog.GROUP_ADD_REQUEST_KEY,
            this,
            FragmentResultListener { _: String, result: Bundle ->
                val fields =
                    result.getParcelable(CreateGroupDialog.GROUP_FIELDS) as? SimpleGroupFields
                fields?.let { mPresenter.processSaveGroup(it) }
            })

        parentFragmentManager.setFragmentResultListener(
            PaletteDialog.PALETTE_REQUEST_KEY,
            this,
            FragmentResultListener { _: String, result: Bundle ->
                val color = result.getInt(PaletteDialog.PALETTE_DIALOG_RESULT_COLOR)
                mPresenter.setColor(color)
            }
        )

        parentFragmentManager.setFragmentResultListener(
            ImageDialog.IMAGE_SELECTOR_REQUEST_KEY,
            this,
            FragmentResultListener { _: String, result: Bundle ->
                val image = result.getInt(ImageDialog.IMAGE_SELECTOR_RESULT_IMAGE)
                mPresenter.setImage(image)
            }
        )
    }

    private lateinit var binding: MainScreenBinding
    private lateinit var sheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun handleFailure(failure: Failure?) {
        prepareFailure(failure)
    }

    override fun navigateAuth(navDirections: NavDirections) {
        navigate(navDirections)
    }

    override fun navigateCreateUpdateGroupDialog(
        navDirections: NavDirections,
        params: Bundle?
    ) {
        navigate(navDirections)
    }

    override fun showGroups(groups: List<Group>) {
        groupAdapter.setItemList(groups)
        binding.groupList.apply {
            adapter = groupAdapter
            layoutManager = GridLayoutManager(requireContext(), 4)
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
        binding.fabMainScreen.setOnClickListener { mPresenter.clickCreateGroup() }
        binding.separator1.setOnClickListener { mPresenter.clickBtnMyDay() }
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

    inner class GroupClickListener : CompositeAdapter.ClickItemInterface<Group> {
        override fun onClick(item: Group, position: Int) {
            mPresenter.clickGroup(item, position)
        }

        override fun onLongClick(item: Group, position: Int) {
            mPresenter.clickLongGroup(item, position)
        }
    }

    override fun showGroupEditDialog() {
        groupPropertyHelper.slideUp()
        binding.fabMainScreen.visibility = View.INVISIBLE
    }

    override fun hideGroupEditDialog() {
        groupPropertyHelper.slideDown()
        binding.fabMainScreen.visibility = View.VISIBLE
    }

    override fun setSelectValueInBottom(value: Int) {
        groupPropertyHelper.setSelectValue(value)
    }

    override fun activateGroup(position: Int) {
        groupAdapter.notifyItemChangedWithPayload(position, GroupDelegateAdapter.PAYLOAD_ACTIVATE)
    }

    override fun deactivateGroup(position: Int) {
        groupAdapter.notifyItemChangedWithPayload(position, GroupDelegateAdapter.PAYLOAD_DEACTIVATE)
    }

    override fun showPalette() {
        navigate(MainScreenFragmentDirections.actionMainScreenFragmentToPaletteDialog())
    }

    override fun showImageSelector() {
        navigate(MainScreenFragmentDirections.actionMainScreenFragmentToImageDialog())
    }
}
