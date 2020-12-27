package io.github.ovso.dialer.view.ui.dialer

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.github.ovso.dialer.R
import io.github.ovso.dialer.databinding.FragmentDialerBinding
import io.github.ovso.dialer.view.base.DataBindingFragment

@AndroidEntryPoint
class DialerFragment : DataBindingFragment<FragmentDialerBinding>(R.layout.fragment_dialer) {

  override val viewModel: DialerViewModel by viewModels()


  companion object {
    fun newInstance() = DialerFragment()
  }

}
