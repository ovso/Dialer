package io.github.ovso.dialer.view.ui.home

import androidx.fragment.app.viewModels
import io.github.ovso.dialer.R
import io.github.ovso.dialer.databinding.FragmentHomeBinding
import io.github.ovso.dialer.view.base.DataBindingFragment

class HomeFragment : DataBindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
  override val viewModel: HomeViewModel by viewModels()

}
