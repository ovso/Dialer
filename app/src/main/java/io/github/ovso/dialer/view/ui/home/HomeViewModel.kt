package io.github.ovso.dialer.view.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.orhanobut.logger.Logger
import io.github.ovso.dialer.data.HomeRepository
import io.github.ovso.dialer.data.local.model.GroupEntity
import io.github.ovso.dialer.data.toGroupModels
import io.github.ovso.dialer.extensions.toStringTime
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject constructor(
  private val homeRepository: HomeRepository
) : ViewModel() {

  private lateinit var groupsObserver: Observer<List<GroupEntity>>

  private val _groups = MutableLiveData<List<GroupEntity>>()

  init {
    Logger.d("homeRepository: $homeRepository")
    reqGroups()
  }

  private fun reqGroups() {
    groupsObserver = Observer<List<GroupEntity>> {
      Logger.d("groups: $it")
      _groups.value = it
      it.toGroupModels().forEach { groupModel ->
        _addTab.value = groupModel.name
      }
    }
    homeRepository.getGroups().observeForever(groupsObserver)
  }

  private val _text = MutableLiveData<String>().apply {
    value = "This is home Fragment"
  }
  val text: LiveData<String> = _text

  private val _showAddDialog = MutableLiveData<((String) -> Unit)>()
  val showAddDialog: LiveData<((String) -> Unit)> get() = _showAddDialog

  private val _addTab = MutableLiveData<String>()
  val addTab: LiveData<String> get() = _addTab

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
