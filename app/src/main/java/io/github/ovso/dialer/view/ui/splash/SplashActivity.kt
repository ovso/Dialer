package io.github.ovso.dialer.view.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.ovso.dialer.view.ui.main.MainActivity2

class SplashActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Intent(this, MainActivity2::class.java).apply {
      startActivity(this)
      finish()
    }
  }
}
