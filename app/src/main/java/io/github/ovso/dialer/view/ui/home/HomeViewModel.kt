package io.github.ovso.dialer.view.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.orhanobut.logger.Logger
import io.github.ovso.dialer.data.HomeRepository
import io.github.ovso.dialer.data.local.model.GroupEntity
import io.github.ovso.dialer.data.mapper.toGroupEntity
import io.github.ovso.dialer.data.mapper.toGroupModels
import io.github.ovso.dialer.data.view.GroupModel
import io.github.ovso.dialer.extensions.toStringTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject constructor(
  private val repository: HomeRepository
) : ViewModel() {

  private lateinit var groupsObserver: Observer<List<GroupEntity>>

  private val _groups = MutableLiveData<List<GroupModel>>()
  val groups: MutableLiveData<List<GroupModel>> = _groups

  private val _text = MutableLiveData<String>().apply {
    value = "This is home Fragment"
  }
  val text: LiveData<String> = _text

  private val _showAddDialog = MutableLiveData<((String) -> Unit)>()
  val showAddDialog: LiveData<((String) -> Unit)> get() = _showAddDialog

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
    _showAddDialog.value = { text ->
      viewModelScope.launch(Dispatchers.Default) {
        val groupId = System.currentTimeMillis().toStringTime().toLong()
        repository.insertGroup(
          GroupEntity(
            groupId = groupId,
            name = text
          )
        )
      }
    }
  }

  override fun onCleared() {
    super.onCleared()
    repository.getGroups().removeObserver(groupsObserver)
  }

  fun onDeleteGroupClick(position: Int) {
    viewModelScope.launch(Dispatchers.Default) {
      _groups.value?.let { models ->
        repository.deleteGroup(models[position].toGroupEntity())
      }
    }
  }

  fun onUpdateGroupNameClick(position: Int, name: String) {
    _groups.value?.let {
      viewModelScope.launch(Dispatchers.Default) {
        repository.updateGroup(it[position].toGroupEntity(name))
      }
    }
  }
}
