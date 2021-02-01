package io.github.ovso.dialer.view.ui.home

import com.google.android.material.tabs.TabLayout
import com.orhanobut.logger.Logger

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
    Logger.d("onTabSelected($position)")
  }

  open fun onTabUnselected(position: Int) {
    Logger.d("onTabUnselected($position)")
  }

  open fun onTabReselected(position: Int) {
    Logger.d("onTabReselected($position)")
  }
}
