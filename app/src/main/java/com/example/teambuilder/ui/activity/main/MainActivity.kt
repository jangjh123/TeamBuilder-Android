package com.example.teambuilder.ui.activity.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.teambuilder.R
import com.example.teambuilder.ui.fragment.team_build.TeamBuildFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentContainerView =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = fragmentContainerView.findNavController()

        val isMatchExist = intent.getBooleanExtra("isMatchExist", false)

        if (isMatchExist) {
            navController.navigate(
                TeamBuildFragmentDirections.actionFragTeamBuildToFragMatch(
                    null,
                    null
                )
            )
        }
    }
}