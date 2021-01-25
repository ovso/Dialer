package io.github.ovso.dialer.view.ui.dialer

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.orhanobut.logger.Logger
import dagger.hilt.android.AndroidEntryPoint
import io.github.ovso.dialer.R
import io.github.ovso.dialer.data.args.ARGS
import io.github.ovso.dialer.data.args.ContactDialogArgs
import io.github.ovso.dialer.data.args.DialerArgs
import io.github.ovso.dialer.databinding.FragmentDialerBinding
import io.github.ovso.dialer.view.base.DataBindingFragment
import io.github.ovso.dialer.view.ui.dialer.adapter.DialerAdapter

@AndroidEntryPoint
class DialerFragment : DataBindingFragment<FragmentDialerBinding>(R.layout.fragment_dialer) {

  override val viewModel: DialerViewModel by viewModels()
  private val adapter by lazy { DialerAdapter() }
  private var contactsDialog: ContactsDialog? = null

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
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
      Logger.d("dialerItemModel: $it")
      ContactsDialog(
        context = requireContext(),
        launcher = contactsDialogLauncher,
        ContactDialogArgs(
          name = it.name,
          no = it.no,
          color = it.color,
          type = ContactsDialog.Type.Update,
          lifecycleCoroutineScope = lifecycleScope
        )
      ).apply {
        onOkClickListener = viewModel::onContactsDialogOkClick
        onDelClickListener = viewModel::onContactsDialogDelClick
      }.show().also { dialog ->
        contactsDialog = dialog
      }
    }

    viewModel.showAddDialog.observe(owner) {
      ContactsDialog(
        requireContext(),
        contactsDialogLauncher,
        args = ContactDialogArgs(
          name = "",
          no = "",
          type = ContactsDialog.Type.Insert,
          color = "",
          lifecycleCoroutineScope = lifecycleScope
        )
      ).apply {
        onOkClickListener = viewModel::onContactsDialogOkClick
      }.show().also {
        contactsDialog = it
      }
    }
  }

  private var contactsDialogLauncher: ActivityResultLauncher<Intent> =
    registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
      contactsDialog?.onContactsResult(it)
    }

  companion object {
    fun newInstance(args: DialerArgs): DialerFragment {
      return DialerFragment().apply {
        arguments = bundleOf(ARGS to args)
      }
    }
  }
}
