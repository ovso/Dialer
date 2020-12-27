package io.github.ovso.dialer.view.ui.dialer

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.orhanobut.logger.Logger
import io.github.ovso.dialer.data.view.DialerItemModel

class DialerViewModel @ViewModelInject constructor() : ViewModel() {

  private val _items = MutableLiveData<List<DialerItemModel>>()
  val items: LiveData<List<DialerItemModel>> get() = _items

  private val _setupAdapter = MutableLiveData<((DialerItemModel) -> Unit)>()
  val setupAdapter: LiveData<((DialerItemModel) -> Unit)> get() = _setupAdapter

  private val _showEditDialog = MutableLiveData<DialerItemModel>()
  val showEditDialog: LiveData<DialerItemModel> get() = _showEditDialog

  private val _showAddDialog = MutableLiveData<DialerItemModel>()
  val showAddDialog: LiveData<DialerItemModel> get() = _showAddDialog

  init {
    _setupAdapter.value = ::onItemClick
    _items.value = listOf(
      DialerItemModel("peter", "010-3429-4620"),
      DialerItemModel("peter", "010-3429-4620"),
      DialerItemModel("peter", "010-3429-4620"),
      DialerItemModel("peter", "010-3429-4620", footer = true),
    )
  }

  private fun onItemClick(item: DialerItemModel) {
    Logger.d("item: $item")
    when (item.footer) {
      true -> _showAddDialog.value = item
      else -> _showEditDialog.value = item
    }
  }
}
