package com.example.teambuilder.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("setAdapter")
    fun setAdapter(
        view: RecyclerView,
        adapter: ListAdapter<Any, ViewHolder>
    ) {
        view.adapter = adapter
    }

    @JvmStatic
    @BindingAdapter("setRecyclerViewAdapter")
    fun setRecyclerViewAdapter(
        view: RecyclerView,
        adapter: RecyclerView.Adapter<ViewHolder>
    ) {
        view.adapter = adapter
    }

    @JvmStatic
    @BindingAdapter("setLayoutManager")
    fun setLayoutManager(view: RecyclerView, orientation: Int) {
        view.layoutManager = LinearLayoutManager(view.context, orientation, false)
    }

    @JvmStatic
    @BindingAdapter("setLinearSnapHelper")
    fun setLinearSnapHelper(view: RecyclerView, boolean: Boolean) {
        if (boolean) {
            LinearSnapHelper().attachToRecyclerView(view)
        }
    }
}