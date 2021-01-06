package io.github.ovso.dialer.view.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.orhanobut.logger.Logger
import io.github.ovso.dialer.data.HomeRepository
import io.github.ovso.dialer.data.local.model.GroupEntity
import io.github.ovso.dialer.data.toGroupModels
import io.github.ovso.dialer.extensions.toStringTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject constructor(
  private val homeRepository: HomeRepository
) : ViewModel() {

  private lateinit var groupsObserver: Observer<List<GroupEntity>>

  private val _groups = MutableLiveData<List<GroupEntity>>()

  private val _text = MutableLiveData<String>().apply {
    value = "This is home Fragment"
  }
  val text: LiveData<String> = _text

  private val _showAddDialog = MutableLiveData<((String) -> Unit)>()
  val showAddDialog: LiveData<((String) -> Unit)> get() = _showAddDialog

  private val _addTabs = MutableLiveData<List<String>>()
  val addTabs: LiveData<List<String>> get() = _addTabs

  init {
    Logger.d("homeRepository: $homeRepository")
    reqGroups()
  }

  private fun reqGroups() {
    groupsObserver = Observer<List<GroupEntity>> {
      Logger.d("groups: $it")
      viewModelScope.launch(Dispatchers.IO) {
        _groups.postValue(it)
        _addTabs.postValue(
          it.toGroupModels().map { model ->
            model.name
          }
        )
      }
    }
    homeRepository.getGroups().observeForever(groupsObserver)
  }

  fun onFabClick() {
    _showAddDialog.value = { text ->
      viewModelScope.launch {
        val groupId = System.currentTimeMillis().toStringTime("yyyyMMddHHmmss").toLong()
        homeRepository.insertGroup(
          GroupEntity(
            groupId = groupId,
            name = text,
            index = 0
          )
        )
      }
    }
  }

  override fun onCleared() {
    super.onCleared()
    homeRepository.getGroups().removeObserver(groupsObserver)
  }

  fun onDeleteGroupClick(position: Int) {
    _groups.value?.let { groups ->
      viewModelScope.launch {
        homeRepository.deleteGroup(groups[position])
      }
    }
  }
}
