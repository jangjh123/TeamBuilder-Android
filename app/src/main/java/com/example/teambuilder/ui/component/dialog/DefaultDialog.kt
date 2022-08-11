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
import com.example.teambuilder.databinding.DialogDefaultBinding

class DefaultDialog(
    private val title: String,
    private val body: String,
    private val cancelText: String,
    private val confirmText: String,
    private val confirmResult: Any?,
    private inline val onClickCancel: (() -> Unit)?,
    private inline val onClickConfirm: ((Any?) -> Unit)?
) : DialogFragment() {
    private lateinit var binding: DialogDefaultBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_default, container, false)
        binding.root.background = ColorDrawable(Color.TRANSPARENT)
        binding.dialog = this@DefaultDialog
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
            tvTitle.text = title
            tvBody.text = body
            btnCancel.text = cancelText
            btnConfirm.text = confirmText
        }
    }

    fun onClickCancel(view: View) {
        onClickCancel?.invoke()
        dismiss()
    }

    fun onClickConfirm(view: View) {
        if (confirmResult != null) {
            onClickConfirm?.invoke(confirmResult)
        } else {
            onClickConfirm?.invoke(null)
        }
        dismiss()
    }
}