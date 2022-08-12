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
import com.example.teambuilder.databinding.DialogChoiceBinding
import com.example.teambuilder.ui.component.adapter.PlayerAdapter
import com.google.android.material.snackbar.Snackbar

class ChoiceDialog(
    private val players: List<Player>,
    private val title: String,
    private inline val onChosen: (Player) -> Unit
) : DialogFragment() {

    private lateinit var binding: DialogChoiceBinding

    private val adapter = PlayerAdapter(
        false,
        onClickPlayer = {
        if (it.team == Team.NONE) { // 어느 팀에도 속하지 않았다면
            onChosen(it)
            dismiss()
        } else {
            Snackbar.make(binding.root, "이미 선택된 선수입니다.", Snackbar.LENGTH_SHORT).show()
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_choice, container, false)
        binding.root.background = ColorDrawable(Color.TRANSPARENT)
        binding.dialog = this@ChoiceDialog
        binding.adapter = adapter

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
        binding.tvTitle.text = title
        adapter.submitList(players)
    }
}