package io.github.ovso.dialer.view.ui.dialer

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.orhanobut.logger.Logger
import io.github.ovso.dialer.data.HomeRepository
import io.github.ovso.dialer.data.args.ARGS
import io.github.ovso.dialer.data.args.DialerArgs
import io.github.ovso.dialer.data.local.model.ContactEntity
import io.github.ovso.dialer.data.mapper.toContactEntity
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

  private var args = savedStateHandle.get<DialerArgs>(ARGS) ?: DialerArgs(-1)

  init {
    _setupAdapter.value = ::onItemClick
    observe()
  }

  private fun observe() {
    Logger.d("observe groupId: ${args.groupId}")
    repository.getContacts(args.groupId).observeForever { entities ->
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
      true -> _showAddDialog.value = item.copy(groupId = args.groupId)
      else -> _showEditDialog.value = item
    }
  }

  fun onContactsDialogOkClick(model: ContactsDialogModel) {

    suspend fun insertContact(model: ContactsDialogModel) {
      Logger.d("insertContact: $model")
      val contactId = System.currentTimeMillis().toStringTime().toLong()
      repository.insertContact(
        entity = ContactEntity(
          contactId = contactId,
          name = model.nm,
          no = model.no,
          color = model.color,
          parent = model.groupId
        ).apply {
          Logger.d("entity: $this")
        }
      )
    }

    suspend fun updateContact(model: ContactsDialogModel) {
      _showEditDialog.value?.let { dialerItemModel ->
        repository.updateContact(
          model.toContactEntity(
            contactId = dialerItemModel.contactId,
            parent = model.groupId
          )
        )
      }
    }

    viewModelScope.launch(Dispatchers.IO) {
      when (model.type) {
        is ContactsDialog.Type.Insert -> insertContact(model)
        is ContactsDialog.Type.Update -> updateContact(model)
      }
    }
  }

  fun onContactsDialogDelClick(model: ContactsDialogModel) {
    viewModelScope.launch(Dispatchers.IO) {
      _showEditDialog.value?.let {
        repository.deleteContact(model.toContactEntity(it.contactId, args.groupId))
      }
    }
  }
}
