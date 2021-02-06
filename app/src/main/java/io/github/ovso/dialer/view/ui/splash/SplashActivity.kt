package io.github.ovso.dialer.view.ui.splash

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.orhanobut.logger.Logger
import io.github.ovso.dialer.R
import io.github.ovso.dialer.view.ui.main.MainActivity2

class SplashActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val requestLocation = registerForActivityResult(
      ActivityResultContracts.RequestPermission(),
      Manifest.permission.CALL_PHONE
    ) { isGranted ->
      Logger.d("isGranted: $isGranted")
      when (isGranted) {
        true -> navigateToMain()
        false -> showNotGrantedDialog()
      }
    }

    requestLocation.launch(Unit)
  }

  private fun showNotGrantedDialog() {
    AlertDialog.Builder(this)
      .setIcon(R.drawable.ic_warning)
      .setMessage(R.string.permission_not_granted_msg)
      .setCancelable(false)
      .setPositiveButton(R.string.end) { dialog, _ ->
        dialog.dismiss()
        finish()
      }
      .setNeutralButton(R.string.go_to_settings) { dialog, _ ->
        dialog.dismiss()
        navigateToSetting()
        finish()
      }
      .show()
  }

  private fun navigateToSetting() {
    try {
      Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.parse("package:$packageName")
      ).apply {
        startActivity(this)
      }
    } catch (ex: Exception) {
      Logger.e(ex, ex.message.toString())
    }
  }

  private fun navigateToMain() {
    Intent(this, MainActivity2::class.java).apply {
      startActivity(this)
      finish()
    }
  }
}
