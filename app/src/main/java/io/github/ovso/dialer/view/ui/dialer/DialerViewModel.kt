package io.github.ovso.dialer.view.ui.dialer

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.ovso.dialer.data.view.DialerItemModel

class DialerViewModel @ViewModelInject constructor() : ViewModel() {

  private val _items = MutableLiveData<List<DialerItemModel>>()
  val items: LiveData<List<DialerItemModel>> get() = _items

  init {
    _items.value = listOf(
      DialerItemModel("peter", "010-3429-4620"),
      DialerItemModel("peter", "010-3429-4620"),
      DialerItemModel("peter", "010-3429-4620"),
      DialerItemModel("peter", "010-3429-4620", footer = true),
    )
  }
}
