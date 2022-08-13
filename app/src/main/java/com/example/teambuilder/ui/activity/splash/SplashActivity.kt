package com.example.teambuilder.ui.activity.splash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.teambuilder.R
import com.example.teambuilder.ui.activity.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        setFirstFragment()
    }

    private fun setFirstFragment() {
        viewModel.getMatchExist { isMatchExist ->
            CoroutineScope(Dispatchers.Default).launch {
//                delay(2000L)
                startActivity(Intent(this@SplashActivity, MainActivity::class.java)
                    .putExtra("isMatchExist", isMatchExist))
                finish()
            }
        }
    }
}