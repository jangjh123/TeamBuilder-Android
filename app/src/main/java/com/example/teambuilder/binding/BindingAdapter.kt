package com.example.teambuilder.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("setAdapter")
    fun setAdapter(
        view: RecyclerView,
        adapter: ListAdapter<Any, RecyclerView.ViewHolder>
    ) {
        view.adapter = adapter
    }

    @JvmStatic
    @BindingAdapter("setLayoutManager")
    fun setLayoutManager(view: RecyclerView, orientation: Int) {
        view.layoutManager = LinearLayoutManager(view.context, orientation, false)
    }
}