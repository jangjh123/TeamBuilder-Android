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
import com.example.teambuilder.data.model.Player
import com.example.teambuilder.data.model.Team
import com.example.teambuilder.databinding.DialogSelectionBinding
import com.example.teambuilder.ui.component.adapter.PlayerAdapter
import com.google.android.material.snackbar.Snackbar

class SelectionDialog(
    private val players: List<Player>,
    private inline val onClickCancel: () -> Unit,
    private inline val onClickConfirm: () -> Unit
) : DialogFragment() {
    private lateinit var binding: DialogSelectionBinding
    private val adapter = PlayerAdapter(
        true,
        onClickPlayer = {
            if (it.team == Team.TEAM_A || it.team == Team.TEAM_B) {
                Snackbar.make(dialog!!.window!!.decorView, "리더는 제외할 수 없습니다.", Snackbar.LENGTH_SHORT)
            } else {
                if (it.team == Team.NONE) {
                    it.team = Team.RANDOM
                } else if (it.team == Team.RANDOM) {
                    it.team = Team.NONE
                }
            }
        })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_selection, container, false)
        binding.root.background = ColorDrawable(Color.TRANSPARENT)
        binding.dialog = this@SelectionDialog
        binding.adapter = adapter
        isCancelable = false
        initView()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        setBackgroundTransparent()
    }

    private fun setBackgroundTransparent() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun initView() {
        with(binding) {

        }
    }

    fun onClickCancel(view: View) {
        onClickCancel()
        players.forEach {
            // Team.NONE 으로 변경했던 것 취소.
        }
        dismiss()
    }

    fun onClickConfirm(view: View) {
        onClickConfirm()
    }
}