package io.github.ovso.dialer.view.ui.splash

import android.Manifest
import android.content.Intent
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
        true -> {
          Intent(this, MainActivity2::class.java).apply {
            startActivity(this)
            finish()
          }
        }
        false -> {
          val appName = getString(R.string.app_name)
          val msg = getString(R.string.permission_not_granted_msg, appName)
          AlertDialog.Builder(this)
            .setIcon(R.drawable.ic_warning)
            .setMessage(msg)
            .setPositiveButton(R.string.end) { dialog, _ ->
              dialog.dismiss()
              finish()
            }
            .setNeutralButton(R.string.go_to_settings) { dialog, _ ->
              dialog.dismiss()
              val actionMain = Intent(Intent.ACTION_MAIN)
                .setAction(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
              val mSettingsIntent: Intent = actionMain
              try {
                startActivity(mSettingsIntent)
              } catch (ex: Exception) {
                Logger.e(ex, ex.message.toString())
              }
              finish()
            }
            .show()
        }
      }
    }

    requestLocation.launch(Unit)
  }
}
