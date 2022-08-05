package com.example.teambuilder.ui.component

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
import com.example.teambuilder.databinding.DialogChoiceBinding

class ChoiceDialog(
    private val players: List<Player>,
    private val title: String,
    private inline val onChosen: (Player) -> Unit
) : DialogFragment() {

    private lateinit var binding: DialogChoiceBinding

    private val adapter = PlayerAdapter(onClickPlayer = {
        if (it.team == 0) { // 어느 팀에도 속하지 않았다면
            dismiss()
        }
        onChosen(it)
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