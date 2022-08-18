package com.example.teambuilder.ui.fragment.match_history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.teambuilder.data.model.Match
import com.example.teambuilder.data.repository.MatchHistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchHistoryViewModel @Inject constructor(
    private val repository: MatchHistoryRepository
) : ViewModel() {

    private val _matches = MutableLiveData<List<Match>>()
    val matches: LiveData<List<Match>>
        get() = _matches

    fun getAllMatch() {
        CoroutineScope(Dispatchers.Main).launch {
            _matches.postValue(repository.getAllMatchFromRoom())
        }
    }
}