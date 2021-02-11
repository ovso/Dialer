package io.github.ovso.dialer.view.ui.home

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.orhanobut.logger.Logger
import dagger.hilt.android.AndroidEntryPoint
import io.github.ovso.dialer.R
import io.github.ovso.dialer.data.HomeRepository
import io.github.ovso.dialer.data.mapper.toGroupModifyDialogModel
import io.github.ovso.dialer.data.prefs.ConfigDataStore
import io.github.ovso.dialer.databinding.DialogHomeAddGroupBinding
import io.github.ovso.dialer.databinding.FragmentHomeBinding
import io.github.ovso.dialer.extensions.loadAdaptiveBanner
import io.github.ovso.dialer.extensions.showInterstitialAd
import io.github.ovso.dialer.view.base.DataBindingFragment
import io.github.ovso.dialer.view.ui.help.GuideFragment
import io.github.ovso.dialer.view.ui.home.adapter.HomePagerAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
class HomeFragment : DataBindingFragment<FragmentHomeBinding>(R.layout.fragment_home),
  CoroutineScope {

  private val job = Job()
  override val coroutineContext: CoroutineContext
    get() = job + Dispatchers.Main

  override val viewModel: HomeViewModel by viewModels()

  private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

  @Inject
  lateinit var repository: HomeRepository

  @Inject
  lateinit var configDataStore: ConfigDataStore

  override fun onStart() {
    super.onStart()
    setHasOptionsMenu(true)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupToolbarAndDrawer()
    addEvent()
    observe()
    binding.adContainer.loadAdaptiveBanner()
    launch {
      configDataStore.firstRunFlow.collect {
        when (it) {
          true -> {
            configDataStore.storeConfig(firstRun = false) // 기본값이 true
            GuideFragment.newInstance()
              .show(childFragmentManager, GuideFragment::class.java.simpleName)
          }
          else -> Logger.d("Not first run")
        }

      }
    }
  }

  private fun setupTabMediator() {
    (binding.vpHome.adapter as? HomePagerAdapter)?.let { adapter ->
      TabLayoutMediator(binding.tabs, binding.vpHome) { tabs, position ->
        tabs.text = adapter.items[position].name
      }.attach()
    }
  }

  private fun setupVp() {
    HomePagerAdapter(this).also {
      binding.vpHome.adapter = null
      binding.vpHome.adapter = it
    }
  }

  private fun observe() {
    val owner = viewLifecycleOwner
    viewModel.showGroupAddDialog.observe(owner) {
      showAddDialog(it)
    }

    viewModel.groups.observe(owner) { groupModels ->
      setupVp()
      setupTabMediator()
      (binding.vpHome.adapter as? HomePagerAdapter)?.let {
        it.items.addAll(groupModels)
        it.notifyDataSetChanged()
      }
    }

    viewModel.showGroupModifyDialog.observe(owner) {
      Logger.d("GroupModifyDialog")
      val selectedTabPosition = binding.tabs.selectedTabPosition
      GroupModifyDialog(
        context = requireContext(),
        tabText = binding.tabs.getTabAt(selectedTabPosition)?.text.toString(),
        lifecycleCoroutineScope = lifecycleScope,
        repository = repository,
        model = it.toGroupModifyDialogModel(),
        onDelClickListener = {
          showInterstitialAd()
        }
      ).show()
    }

    configDataStore.firstRunFlow.asLiveData().observe(owner) {

    }
  }

  private fun showAddDialog(listener: ((String) -> Unit)?) {
    Logger.d("showAddDialog")
    val binding = DialogHomeAddGroupBinding.inflate(requireActivity().layoutInflater)
    val onOkClick = DialogInterface.OnClickListener { dialog, _ ->
      dialog.dismiss()
      listener?.invoke(binding.etGroupAddDialog.text?.trim().toString())
      showInterstitialAd()
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
    binding.navView.setNavigationItemSelectedListener {
      binding.drawerLayout.closeDrawer(GravityCompat.START, true)
      showHelpDialog()
      true
    }

    binding.tabs.addOnTabSelectedListener(object : OnSimpleTabSelectedListener() {
      override fun onTabReselected(tab: TabLayout.Tab) {
        super.onTabReselected(tab)
        viewModel.onTabReselected(tab.position)
      }
    })
  }

  private fun showHelpDialog() {
    GuideFragment.newInstance().show(childFragmentManager, GuideFragment::class.java.simpleName)
  }

  private fun setupToolbarAndDrawer() {
    (requireActivity() as? AppCompatActivity)?.let {
      it.setSupportActionBar(binding.includeToolbar.toolbar)
      it.title = getString(R.string.app_name)
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
