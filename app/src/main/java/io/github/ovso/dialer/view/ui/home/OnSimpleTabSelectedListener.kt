package io.github.ovso.dialer.view.ui.home

import com.google.android.material.tabs.TabLayout

abstract class OnSimpleTabSelectedListener : TabLayout.OnTabSelectedListener {
  override fun onTabSelected(tab: TabLayout.Tab) {
    onTabSelected(tab.position)
  }

  override fun onTabUnselected(tab: TabLayout.Tab) {
    onTabUnselected(tab.position)
  }

  override fun onTabReselected(tab: TabLayout.Tab) {
    onTabReselected(tab.position)
  }

  open fun onTabSelected(position: Int) {

  }

  open fun onTabUnselected(position: Int) {

  }

  open fun onTabReselected(position: Int) {

  }
}
