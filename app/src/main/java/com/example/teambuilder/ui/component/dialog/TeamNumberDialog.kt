package com.example.teambuilder.ui.component.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.teambuilder.R
import com.example.teambuilder.databinding.DialogTeamNumberBinding

class TeamNumberDialog(
    private inline val onClickTwoTeam: (Int) -> Unit,
    private inline val onClickThreeTeam: (Int) -> Unit
) : DialogFragment() {
    private lateinit var binding: DialogTeamNumberBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_team_number, container, false)
        binding.root.background = ColorDrawable(Color.TRANSPARENT)
        binding.dialog = this@TeamNumberDialog
        isCancelable = false

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        setBackgroundTransparent()
    }

    private fun setBackgroundTransparent() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    fun setTwoTeamBuilder(view: View) {
        onClickTwoTeam(2)
        dismiss()
    }

    fun setThreeTeamBuilder(view: View) {
        onClickThreeTeam(3)
        dismiss()
    }
}