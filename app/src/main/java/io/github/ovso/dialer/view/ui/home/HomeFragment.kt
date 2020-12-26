package io.github.ovso.dialer.view.ui.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import io.github.ovso.dialer.R
import io.github.ovso.dialer.databinding.FragmentHomeBinding
import io.github.ovso.dialer.view.base.DataBindingFragment

class HomeFragment : DataBindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
  override val viewModel: HomeViewModel by viewModels()

  private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

  override fun onStart() {
    super.onStart()
    setHasOptionsMenu(true)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupToolbarAndDrawer()
  }

  private fun setupToolbarAndDrawer() {
    (requireActivity() as? AppCompatActivity)?.let {
      it.setSupportActionBar(binding.includeToolbar.toolbar)
      it.title = "Speed Dialer"
    }
    actionBarDrawerToggle = ActionBarDrawerToggle(
      activity,
      binding.drawerLayout,
      binding.includeToolbar.toolbar,
      R.string.navigation_drawer_open,
      R.string.navigation_drawer_close
    ).also {
      it.syncState()
      binding.drawerLayout.addDrawerListener(it)
    }
  }

}
