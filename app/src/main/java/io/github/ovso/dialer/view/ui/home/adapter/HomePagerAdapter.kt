package io.github.ovso.dialer.view.ui.home.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import io.github.ovso.dialer.view.ui.dialer.DialerFragment

class HomePagerAdapter(fm: Fragment) : FragmentStateAdapter(fm) {
  var items = mutableListOf<String>()
  override fun getItemCount(): Int = items.count()

  override fun createFragment(position: Int): Fragment = DialerFragment.newInstance()
}
