package com.lamp.planner.presentation.features.groupproperty

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lamp.planner.databinding.FragmentGroupPropertyBinding

class GroupPropertyFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentGroupPropertyBinding

    //    private val args: EventDialogArgs by navArgs()
//
//    @Inject
//    lateinit var presenterProvider: EventPresenter
//    private val mPresenter by moxyPresenter { presenterProvider }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGroupPropertyBinding.inflate(inflater, container, false)

        return binding.root
    }
}
