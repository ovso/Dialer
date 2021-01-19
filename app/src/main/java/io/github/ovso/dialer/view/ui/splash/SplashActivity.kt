package io.github.ovso.dialer.view.ui.splash

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.orhanobut.logger.Logger
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
          AlertDialog.Builder(this)
            .setTitle("권한을 설정하셔야 합니다.")
            .setMessage("권한을 설정해주세요.")
            .setPositiveButton(android.R.string.ok, null)
            .setNegativeButton(android.R.string.cancel, null)
            .show()
        }
      }
    }

    requestLocation.launch(Unit)
  }
}
