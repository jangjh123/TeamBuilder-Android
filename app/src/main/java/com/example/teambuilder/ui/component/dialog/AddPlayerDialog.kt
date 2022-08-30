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
import com.example.teambuilder.databinding.DialogAddPlayerBinding
import com.google.android.material.snackbar.Snackbar

class AddPlayerDialog(
    private inline val onComplete: (Pair<String, String>) -> Unit
) : DialogFragment() {
    private lateinit var binding: DialogAddPlayerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_add_player, container, false)
        binding.root.background = ColorDrawable(Color.TRANSPARENT)
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
            btnCancel.setOnClickListener {
                dismiss()
            }

            btnEnroll.setOnClickListener {
                if (etName.text.isEmpty()) {
                    Snackbar.make(dialog!!.window!!.decorView, "이름을 입력해주세요.", Snackbar.LENGTH_SHORT)
                        .show()
                } else {
                    val affiliation = when {
                        rb0.isChecked -> {
                            "경북산학융합원"
                        }
                        rb1.isChecked -> {
                            "새마을세계화재단"
                        }
                        rb2.isChecked -> {
                            "경북지역 인자위"
                        }
                        rb3.isChecked -> {
                            "한국여성경제인협회"
                        }
                        else -> {
                            "Free Agent"
                        }
                    }
                    onComplete(Pair(etName.text.toString(), affiliation))
                    dismiss()
                }
            }
        }
    }
}