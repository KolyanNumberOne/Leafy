package com.example.leafy.ui

import android.Manifest
import android.app.AlarmManager
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.leafy.ui.theme.PlantGuideTheme
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val permissionsRequired = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    private val REQUEST_CODE = 100

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkPermissions()
        checkExactAlarmPermission()

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                Log.d("MainActivity", "Token: $token")
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

    private fun checkPermissions() {
        val missingPermissions = permissionsRequired.filter {
            ContextCompat.checkSelfPermission(this, it) != android.content.pm.PackageManager.PERMISSION_GRANTED
        }

        if (missingPermissions.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                missingPermissions.toTypedArray(),
                REQUEST_CODE
            )
        }
    }

    private fun checkExactAlarmPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            if (!alarmManager.canScheduleExactAlarms()) {
                showExactAlarmPermissionDialog()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun showExactAlarmPermissionDialog() {
        AlertDialog.Builder(this)
            .setTitle("Требуется разрешение для уведомлений")
            .setMessage("Для работы уведомлений необходимо разрешение. Вы хотите предоставить его?")
            .setPositiveButton("Да") { _, _ ->
                requestExactAlarmPermission()
            }
            .setNegativeButton("Нет") { _, _ ->
                Toast.makeText(
                    this,
                    "Разрешение не предоставлено. Уведомления не будут работать.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .show()
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun requestExactAlarmPermission() {
        val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
        startActivity(intent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
            if (grantResults.all { it == android.content.pm.PackageManager.PERMISSION_GRANTED }) {
                //Toast.makeText(this, "Все разрешения предоставлены.", Toast.LENGTH_SHORT).show()
            } else {
                //Toast.makeText(this, "Некоторые разрешения отклонены.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
