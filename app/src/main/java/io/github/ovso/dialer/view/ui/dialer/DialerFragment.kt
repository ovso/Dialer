package io.github.ovso.dialer.view.ui.dialer

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.github.ovso.dialer.R
import io.github.ovso.dialer.databinding.FragmentDialerBinding
import io.github.ovso.dialer.view.base.DataBindingFragment
import io.github.ovso.dialer.view.ui.ContactsDialog
import io.github.ovso.dialer.view.ui.dialer.adapter.DialerAdapter

@AndroidEntryPoint
class DialerFragment : DataBindingFragment<FragmentDialerBinding>(R.layout.fragment_dialer) {

  override val viewModel: DialerViewModel by viewModels()

  private val adapter by lazy { DialerAdapter() }

  private lateinit var contactsDialog: ContactsDialog

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    contactsDialog = ContactsDialog(this)
    observe()
  }

  private fun observe() {
    val owner = viewLifecycleOwner
    viewModel.items.observe(owner) {
      adapter.submitList(it)
    }
    viewModel.setupAdapter.observe(owner) { listener ->
      binding.rvDialer.apply {
        adapter = this@DialerFragment.adapter.apply { itemClickListener = listener }
        addItemDecoration(GridItemDecoration(resources.displayMetrics))
      }
    }

    viewModel.showEditDialog.observe(owner) {
      AlertDialog.Builder(requireContext()).apply {
        setMessage("수정 팝업")
        show()
      }
    }

    viewModel.showAddDialog.observe(owner) {
      contactsDialog.show()
    }
  }


  companion object {
    fun newInstance() = DialerFragment()
  }

}
