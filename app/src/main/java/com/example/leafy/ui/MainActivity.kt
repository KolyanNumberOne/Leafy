package com.example.leafy.ui

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.leafy.ui.theme.PlantGuideTheme
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        if (!alarmManager.canScheduleExactAlarms()) {
            val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
            startActivity(intent)
        }

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                Log.d("MainActivity", "Token: $token")
//                Toast.makeText(this, "Token: $token", Toast.LENGTH_SHORT).show()
            } else {
                Log.w("MainActivity", "Fetching FCM registration token failed", task.exception)
            }
        }

        setContent {

            var isDarkTheme by rememberSaveable { mutableStateOf(true) }

            PlantGuideTheme(darkTheme = isDarkTheme) {
                Surface(color = MaterialTheme.colorScheme.background) {
                    Navigation(
                        onThemeToggle = { isDarkTheme = it },
                        isDarkTheme = isDarkTheme
                    )
                }
            }
        }
    }
    //функция вызова уведомлени здесь была
}

