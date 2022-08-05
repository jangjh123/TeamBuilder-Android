package com.example.teambuilder.ui.component

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.teambuilder.R
import com.example.teambuilder.databinding.FragmentMemberPickerBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MemberPickerFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentMemberPickerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_member_picker, container, false)
        isCancelable = false
        return binding.root
    }

    override fun getTheme(): Int {
        return R.style.CustomBottomSheetDialogTheme
    }
}