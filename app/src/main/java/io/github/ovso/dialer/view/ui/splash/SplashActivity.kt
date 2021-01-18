package io.github.ovso.dialer.view.ui.splash

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
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
          Logger.d("퍼미션 거부..")
        }
      }
    }

    requestLocation.launch(Unit)
  }
}
