package io.github.ovso.dialer.view.ui.main

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.navigation.NavigationView
import io.github.ovso.dialer.R
import io.github.ovso.dialer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(),
  NavigationView.OnNavigationItemSelectedListener {
  private lateinit var binding: ActivityMainBinding

  private lateinit var appBarConfiguration: AppBarConfiguration
  private lateinit var findNavController: NavController

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    setSupportActionBar(binding.appBarMain.toolbar)
    supportActionBar?.setDisplayShowCustomEnabled(true)
    val navHostFragment =
      supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
    findNavController = navHostFragment.findNavController()
    appBarConfiguration = AppBarConfiguration(findNavController.graph)
    setupActionBarWithNavController(findNavController, appBarConfiguration)

    val toggle = ActionBarDrawerToggle(
      this,
      binding.drawerLayout, binding.appBarMain.toolbar,
      R.string.navigation_drawer_open,
      R.string.navigation_drawer_close
    )
    binding.drawerLayout.addDrawerListener(toggle)
    toggle.syncState()
    binding.navView.setNavigationItemSelectedListener(this)
    binding.appBarMain.fab.setOnClickListener {
      findNavController.navigate(R.id.nav_gallery)
    }
  }

  override fun onSupportNavigateUp(): Boolean {
    return findNavController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
  }

  override fun onNavigationItemSelected(item: MenuItem): Boolean {
//    binding.drawerLayout.closeDrawer(GravityCompat.START)
    return true
  }
}
