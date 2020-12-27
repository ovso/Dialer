package io.github.ovso.dialer.view.ui.dialer

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.github.ovso.dialer.R
import io.github.ovso.dialer.databinding.FragmentDialerBinding
import io.github.ovso.dialer.view.base.DataBindingFragment
import io.github.ovso.dialer.view.ui.dialer.adapter.DialerAdapter

@AndroidEntryPoint
class DialerFragment : DataBindingFragment<FragmentDialerBinding>(R.layout.fragment_dialer) {

  override val viewModel: DialerViewModel by viewModels()

  private val adapter by lazy { DialerAdapter() }
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupRv()
    observe()
  }

  private fun observe() {
    val owner = viewLifecycleOwner
    viewModel.items.observe(owner) {
      adapter.submitList(it)
    }
  }

  private fun setupRv() {
    binding.rvDialer.adapter = adapter
  }

  companion object {
    fun newInstance() = DialerFragment()
  }

}
