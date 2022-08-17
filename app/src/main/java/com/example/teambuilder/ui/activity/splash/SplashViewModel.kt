package com.example.teambuilder.ui.activity.splash

import androidx.lifecycle.ViewModel
import com.example.teambuilder.data.repository.SplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val repository: SplashRepository) : ViewModel() {

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