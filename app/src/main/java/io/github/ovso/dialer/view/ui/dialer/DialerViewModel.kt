package io.github.ovso.dialer.view.ui.dialer

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.orhanobut.logger.Logger
import io.github.ovso.dialer.data.HomeRepository
import io.github.ovso.dialer.data.args.ARGS
import io.github.ovso.dialer.data.args.DialerArgs
import io.github.ovso.dialer.data.local.model.ContactEntity
import io.github.ovso.dialer.data.mapper.toDialerItemModels
import io.github.ovso.dialer.data.view.ContactsDialogModel
import io.github.ovso.dialer.data.view.DialerItemModel
import io.github.ovso.dialer.extensions.toStringTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DialerViewModel @ViewModelInject constructor(
  private val repository: HomeRepository,
  @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

  private val _items = MutableLiveData<List<DialerItemModel>>()
  val items: LiveData<List<DialerItemModel>> get() = _items

  private val _setupAdapter = MutableLiveData<((DialerItemModel) -> Unit)>()
  val setupAdapter: LiveData<((DialerItemModel) -> Unit)> get() = _setupAdapter

  private val _showEditDialog = MutableLiveData<DialerItemModel>()
  val showEditDialog: LiveData<DialerItemModel> get() = _showEditDialog

  private val _showAddDialog = MutableLiveData<DialerItemModel>()
  val showAddDialog: LiveData<DialerItemModel> get() = _showAddDialog

  init {
    savedStateHandle.get<DialerArgs>(ARGS)?.let {
      Logger.d("args: $it")
    }
    _setupAdapter.value = ::onItemClick
    observe()
  }

  private fun observe() {
    repository.getContacts().observeForever { entities ->
      viewModelScope.launch(Dispatchers.IO) {
        val toDialerItemModels = entities.toDialerItemModels()
        Logger.d("dialerItemModels: $toDialerItemModels")
        _items.postValue(entities.toDialerItemModels())
      }
    }
  }

  private fun onItemClick(item: DialerItemModel) {
    Logger.d("item: $item")
    when (item.footer) {
      true -> _showAddDialog.value = item
      else -> _showEditDialog.value = item
    }
  }

  fun onContactsDialogOkClick(model: ContactsDialogModel) {
    Logger.d("model: $model")
    viewModelScope.launch(Dispatchers.IO) {
      val contactId = System.currentTimeMillis().toStringTime("yyyyMMddHHmmss").toLong()
      repository.insertContact(
        entity = ContactEntity(
          contactId = contactId,
          name = model.nm,
          no = model.no,
          color = "#CCCCCC",
          parent = 1
        )
      )
    }
  }
}
