package io.github.ovso.dialer.view.ui.home

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.orhanobut.logger.Logger
import dagger.hilt.android.AndroidEntryPoint
import io.github.ovso.dialer.R
import io.github.ovso.dialer.data.HomeRepository
import io.github.ovso.dialer.databinding.DialogEditTabNameBinding
import io.github.ovso.dialer.databinding.DialogHomeAddGroupBinding
import io.github.ovso.dialer.databinding.FragmentHomeBinding
import io.github.ovso.dialer.view.base.DataBindingFragment
import io.github.ovso.dialer.view.ui.home.adapter.HomePagerAdapter
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : DataBindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
  override val viewModel: HomeViewModel by viewModels()

  private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

  @Inject
  lateinit var repository: HomeRepository

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
    Logger.d("Logger: $repository")
  }

  private fun setupTabsAndVp() {
    TabLayoutMediator(binding.tabs, binding.vpHome) { tabs, position ->
      tabs.text = adapter.items2[position].name
    }.attach()
    binding.tabs.addOnTabSelectedListener(object : OnSimpleTabSelectedListener() {
      override fun onTabReselected(tab: TabLayout.Tab) {
        super.onTabReselected(tab)
//        showTabModifyDialog(tab)
        showTabModifyDialog2(tab)
      }
    })
  }

  private fun showTabModifyDialog(tab: TabLayout.Tab) {
    AlertDialog.Builder(requireContext())
      .setMessage(getString(R.string.home_delete_group_dialog_msg, tab.text))
      .setPositiveButton(android.R.string.ok) { dialog, _ ->
        dialog.dismiss()
        viewModel.onDeleteGroupClick(tab.position)
      }
      .setNegativeButton(android.R.string.cancel, null)
      .show()
  }

  private fun showTabModifyDialog2(tab: TabLayout.Tab) {
    val binding = DialogEditTabNameBinding.inflate(LayoutInflater.from(requireContext()))
    binding.etDialogEditGroupName.setText(tab.text)
    AlertDialog.Builder(requireContext())
      .setTitle(R.string.all_home_edit_group)
      .setView(binding.root)
      .setPositiveButton(android.R.string.ok) { dialog, _ ->
        dialog.dismiss()
        viewModel.onUpdateGroupNameClick(tab.position, binding.etDialogEditGroupName.text.toString())
      }
      .setNegativeButton(android.R.string.cancel, null)
      .setNeutralButton(R.string.all_tab_del) { dialog, _ ->
        dialog.dismiss()
        viewModel.onDeleteGroupClick(tab.position)
      }
      .show()
  }

  private fun setupVp() {
    binding.vpHome.adapter = adapter
  }

  private fun observe() {
    val owner = viewLifecycleOwner
    viewModel.showAddDialog.observe(owner) {
      showAddDialog(it)
    }

    viewModel.groups.observe(owner) {
      adapter.items2.clear()
      adapter.items2.addAll(it)
      adapter.notifyDataSetChanged()
    }
  }

  private fun showAddDialog(listener: ((String) -> Unit)?) {
    val binding = DialogHomeAddGroupBinding.inflate(requireActivity().layoutInflater)
    val onOkClick = DialogInterface.OnClickListener { dialog, _ ->
      dialog.dismiss()
      listener?.invoke(binding.etGroupAddDialog.text?.trim().toString())
    }
    AlertDialog.Builder(requireContext()).apply {
      setView(binding.root)
      setTitle(R.string.home_add_btn)
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
