package com.example.teambuilder.ui.fragment.multiple_team_build

import androidx.lifecycle.ViewModel
import com.example.teambuilder.data.repository.MultipleTeamBuildRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MultipleTeamBuildViewModel @Inject constructor(private val repository: MultipleTeamBuildRepository) :
    ViewModel() {
}