package io.github.ovso.dialer.view.ui.home.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import io.github.ovso.dialer.data.mapper.toDialerArgs
import io.github.ovso.dialer.data.view.GroupModel
import io.github.ovso.dialer.view.ui.dialer.DialerFragment

class HomePagerAdapter(fm: Fragment) : FragmentStateAdapter(fm) {
  var items2 = mutableListOf<GroupModel>()
  override fun getItemCount(): Int = items2.count()

  override fun createFragment(position: Int): Fragment = DialerFragment.newInstance(
    items2[position].toDialerArgs()
  )
}
