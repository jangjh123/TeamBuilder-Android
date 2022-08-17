package com.example.teambuilder.ui

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment<VB : ViewDataBinding>(private val layoutId: Int) : Fragment() {
    lateinit var binding: VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        setObserver()
        proceed()
        return binding.root
    }

    protected open fun setObserver() {

    }

    protected open fun proceed() {
    }

    protected fun showSnackBar(text: String) {
        Snackbar.make(binding.root, text, Snackbar.LENGTH_SHORT).show()
    }

    protected fun getColor(color: Int) =
        ContextCompat.getColor(requireContext(), color)

    protected fun playColorAnimation(view: View, startColor: Int, endColor: Int) {
        ValueAnimator.ofObject(ArgbEvaluator(), startColor, endColor).apply {
            duration = 200
            addUpdateListener { animator -> view.setBackgroundColor(animator.animatedValue as Int) }
        }.start()
    }

    protected inline fun <T> LiveData<T>.onChanged(crossinline onChanged: (T) -> Unit) {
        this.observe(viewLifecycleOwner) {
            onChanged(it)
        }
    }

    protected inline fun LiveData<Boolean>.isNullOrFalse(crossinline isNullOrFalse: () -> Unit) {
        if (this.value == false) {
            isNullOrFalse()
        } else if (this.value == null) {
            isNullOrFalse()
        }
    }

    protected inline fun LiveData<Boolean>.isTrue(crossinline isTrue: () -> Unit) {
        if (this.value == true) {
            isTrue()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    protected fun setTouchable(view: View, boolean: Boolean) {
        if (boolean) {
            view.setOnTouchListener { _, _ -> false }
        } else {
            view.setOnTouchListener { _, _ -> true }
        }
    }
}

