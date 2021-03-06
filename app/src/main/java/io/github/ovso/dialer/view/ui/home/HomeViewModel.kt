package io.github.ovso.dialer.view.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.orhanobut.logger.Logger
import io.github.ovso.dialer.data.HomeRepository
import io.github.ovso.dialer.data.local.model.GroupEntity
import io.github.ovso.dialer.data.mapper.toGroupModels
import io.github.ovso.dialer.data.view.GroupModel
import io.github.ovso.dialer.extensions.toStringTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject constructor(
  private val repository: HomeRepository,
) : ViewModel() {

  private lateinit var groupsObserver: Observer<List<GroupEntity>>

  private val _groups = MutableLiveData<List<GroupModel>>()
  val groups: MutableLiveData<List<GroupModel>> = _groups

  private val _showGroupAddDialog = MutableLiveData<((String) -> Unit)>()
  val showGroupAddDialog: LiveData<((String) -> Unit)> get() = _showGroupAddDialog

  private val _showGroupModifyDialog = MutableLiveData<GroupModel>()
  val showGroupModifyDialog: LiveData<GroupModel> get() = _showGroupModifyDialog

  init {
    Logger.d("homeRepository: $repository")
    observe()
  }

  private fun observe() {
    groupsObserver = Observer<List<GroupEntity>> {
      Logger.d("groups: $it")
      viewModelScope.launch(Dispatchers.Default) {
        _groups.postValue(it.toGroupModels())
      }
    }
    repository.getGroups().observeForever(groupsObserver)
  }

  fun onFabClick() {
    _showGroupAddDialog.value = { text ->
      viewModelScope.launch(Dispatchers.Default) {
        val groupId = System.currentTimeMillis().toStringTime().toLong()
        repository.insertGroup(
          GroupEntity(
            groupId = groupId,
            name = if (text.isEmpty()) "?" else text
          )
        )
      }
    }
  }

  override fun onCleared() {
    super.onCleared()
    repository.getGroups().removeObserver(groupsObserver)
  }

  fun onTabReselected(tabPosition: Int) {
    _groups.value?.let {
      _showGroupModifyDialog.value = it[tabPosition]
    }
  }
}
