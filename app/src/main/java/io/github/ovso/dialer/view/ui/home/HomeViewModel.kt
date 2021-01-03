package io.github.ovso.dialer.view.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.orhanobut.logger.Logger
import io.github.ovso.dialer.data.HomeRepository
import io.github.ovso.dialer.data.local.model.GroupEntity

class HomeViewModel @ViewModelInject constructor(
  private val homeRepository: HomeRepository
) : ViewModel() {

  private lateinit var groupsObserver:Observer<List<GroupEntity>>

  init {
    Logger.d("homeRepository: $homeRepository")
    reqGroups()
  }

  private fun reqGroups() {
    groupsObserver = Observer<List<GroupEntity>> {
      Logger.d("groups: $it")
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
      Logger.d("text: $text")
      _addTab.value = if (text.isNotEmpty()) text else "?"
    }
  }

  override fun onCleared() {
    super.onCleared()
    homeRepository.getGroups().removeObserver(groupsObserver)
  }
}
