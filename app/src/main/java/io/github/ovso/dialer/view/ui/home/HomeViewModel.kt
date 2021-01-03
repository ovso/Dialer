package io.github.ovso.dialer.view.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import io.github.ovso.dialer.data.HomeRepository
import io.github.ovso.dialer.data.toGroupModels
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel @ViewModelInject constructor(
  private val homeRepository: HomeRepository
) : ViewModel() {

  init {
    Logger.d("repo: $homeRepository")
    viewModelScope.launch {
      reqGroups()
    }
  }

  private suspend fun reqGroups() = withContext(Dispatchers.IO) {
    val groupModels = homeRepository.getGroups().toGroupModels()
    groupModels.forEach {
      _addTab.postValue(it.name)
    }
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
}
