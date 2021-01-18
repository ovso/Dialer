package io.github.ovso.dialer.view.ui.home

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LifecycleCoroutineScope
import io.github.ovso.dialer.R
import io.github.ovso.dialer.data.HomeRepository
import io.github.ovso.dialer.data.mapper.toGroupEntity
import io.github.ovso.dialer.data.view.GroupModifyDialogModel
import io.github.ovso.dialer.databinding.DialogEditTabNameBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GroupModifyDialog(
  private val context: Context,
  private val tabText: String,
  private val lifecycleCoroutineScope: LifecycleCoroutineScope,
  private val repository: HomeRepository,
  private val model: GroupModifyDialogModel
) {

  fun show() {
    val binding = DialogEditTabNameBinding.inflate(LayoutInflater.from(context))
    binding.etDialogEditGroupName.setText(tabText)
    AlertDialog.Builder(context).apply {
      setTitle(R.string.all_home_edit_group)
      setView(binding.root)
      setPositiveButton(android.R.string.ok) { dialog, _ ->
        dialog.dismiss()
        lifecycleCoroutineScope.launch(Dispatchers.Default) {
          repository.updateGroup(
            entity = model.toGroupEntity(binding.etDialogEditGroupName.text.toString())
          )
        }
      }
      setNegativeButton(android.R.string.cancel, null)
      setNeutralButton(R.string.all_tab_del) { dialog, _ ->
        dialog.dismiss()
        lifecycleCoroutineScope.launch(Dispatchers.Default) {
          repository.deleteGroup(
            entity = model.toGroupEntity()
          )
        }
      }
    }.show().apply {
      getButton(DialogInterface.BUTTON_NEUTRAL).setTextColor(Color.RED)
    }
  }
}
