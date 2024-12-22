package com.example.leafy.ui

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.leafy.data.NotificationReceiver
import com.example.leafy.ui.theme.PlantGuideTheme
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //scheduleNotification(this)
        /*FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                Log.d("MainActivity", "Token: $token")
                Toast.makeText(this, "Token: $token", Toast.LENGTH_SHORT).show()
            } else {
                Log.w("MainActivity", "Fetching FCM registration token failed", task.exception)
            }
        }*/ //ну это файербэйз

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

