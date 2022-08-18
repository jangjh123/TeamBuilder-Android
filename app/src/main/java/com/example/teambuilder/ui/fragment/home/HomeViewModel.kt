package com.example.teambuilder.ui.fragment.home

import androidx.lifecycle.ViewModel
import com.example.teambuilder.data.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {
    fun getMatchExist(onResult: (Boolean) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.getDataStoreValue().run {
                cancellable()
                onResult(first())
                cancel()
            }
        }
    }
}