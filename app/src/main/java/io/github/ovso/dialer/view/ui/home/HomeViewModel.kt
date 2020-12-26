package io.github.ovso.dialer.view.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.orhanobut.logger.Logger

class HomeViewModel @ViewModelInject constructor(

) : ViewModel() {

  private val _text = MutableLiveData<String>().apply {
    value = "This is home Fragment"
  }
  val text: LiveData<String> = _text

  private val _showAddDialog = MutableLiveData<((String) -> Unit)>()
  val showAddDialog: LiveData<((String) -> Unit)> get() = _showAddDialog

  fun onFabClick() {
    _showAddDialog.value = { text ->
      Logger.d("text: $text")
    }
  }

}
