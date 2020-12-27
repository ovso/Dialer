package io.github.ovso.dialer.view.ui.home

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.orhanobut.logger.Logger
import dagger.hilt.android.AndroidEntryPoint
import io.github.ovso.dialer.R
import io.github.ovso.dialer.databinding.FragmentHomeBinding
import io.github.ovso.dialer.databinding.ViewHomeAddDialogBinding
import io.github.ovso.dialer.view.base.DataBindingFragment
import io.github.ovso.dialer.view.ui.home.adapter.HomePagerAdapter

@AndroidEntryPoint
class HomeFragment : DataBindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
  override val viewModel: HomeViewModel by viewModels()

  private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

  private val adapter by lazy {
    HomePagerAdapter(this)
  }

  override fun onStart() {
    super.onStart()
    setHasOptionsMenu(true)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupToolbarAndDrawer()
    addEvent()
    observe()
    setupVp()
    setupTabsAndVp()
  }

  private fun setupTabsAndVp() {
    TabLayoutMediator(binding.tabs, binding.vpHome) { tabs, position ->
      Logger.d("text: ${adapter.items[position]}")
      tabs.text = adapter.items[position]
    }.attach()
  }

  private fun setupVp() {
    binding.vpHome.adapter = adapter
  }

  private fun observe() {
    val owner = viewLifecycleOwner
    viewModel.showAddDialog.observe(owner) {
      showAddDialog(it)
    }
    viewModel.addTab.observe(owner) {
      adapter.items.add(it)
      adapter.notifyDataSetChanged()
    }
  }

  private fun showAddDialog(listener: ((String) -> Unit)?) {
    val binding = ViewHomeAddDialogBinding.inflate(requireActivity().layoutInflater)
    val onOkClick = DialogInterface.OnClickListener { dialog, _ ->
      dialog.dismiss()
      listener?.invoke(binding.root.text.trim().toString())
    }
    AlertDialog.Builder(requireContext()).apply {
      setTitle(R.string.home_group_add_dialog_msg)
      setView(binding.root)
      setPositiveButton(android.R.string.ok, onOkClick)
      setNegativeButton(android.R.string.cancel, null)
      show()
    }
  }

  private fun addEvent() {
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
