package com.example.teambuilder.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.teambuilder.ui.component.adapter.TeamBuilderAdapter

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("setListAdapter")
    fun setListAdapter(
        view: RecyclerView,
        adapter: ListAdapter<Any, RecyclerView.ViewHolder>
    ) {
        view.adapter = adapter
    }

    @JvmStatic
    @BindingAdapter("setRecyclerViewAdapter")
    fun setRecyclerViewAdapter(
        view: RecyclerView,
        adapter: RecyclerView.Adapter<TeamBuilderAdapter.ViewHolder>
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