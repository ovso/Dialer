package io.github.ovso.dialer.view.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import io.github.ovso.dialer.R
import io.github.ovso.dialer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(R.layout.activity_main), NavigationView.OnNavigationItemSelectedListener {
  private lateinit var binding: ActivityMainBinding

  private lateinit var appBarConfiguration: AppBarConfiguration
  private lateinit var findNavController: NavController

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val toolbar = findViewById<Toolbar>(R.id.toolbar)
    setSupportActionBar(findViewById(R.id.toolbar))
    supportActionBar?.setDisplayShowCustomEnabled(true)
    val navHostFragment =
      supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
    findNavController = navHostFragment.findNavController()
    appBarConfiguration = AppBarConfiguration(findNavController.graph)
    setupActionBarWithNavController(findNavController, appBarConfiguration)

    val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
    val toggle = ActionBarDrawerToggle(
      this,
      drawerLayout, toolbar,
      R.string.navigation_drawer_open,
      R.string.navigation_drawer_close
    )
    drawerLayout.addDrawerListener(toggle)
    toggle.syncState()
    findViewById<NavigationView>(R.id.nav_view).setNavigationItemSelectedListener(this)

  }

  override fun onSupportNavigateUp(): Boolean {
    val navController = findNavController(R.id.nav_host_fragment_content_main)
    return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
  }

  override fun onNavigationItemSelected(item: MenuItem): Boolean {
    findViewById<DrawerLayout>(R.id.drawer_layout).closeDrawer(GravityCompat.START)
    return true
  }
}
