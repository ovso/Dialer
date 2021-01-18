package io.github.ovso.dialer.view.ui.home

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import io.github.ovso.dialer.R
import io.github.ovso.dialer.databinding.DialogEditTabNameBinding

class GroupModifyDialog(
  private val context: Context,
  private val tabText: String,
) {

  var onOkClickListener: ((String) -> Unit)? = null
  var onDelClickListener: (() -> Unit)? = null

  fun show() {
    val binding = DialogEditTabNameBinding.inflate(LayoutInflater.from(context))
    binding.etDialogEditGroupName.setText(tabText)
    AlertDialog.Builder(context).apply {
      setTitle(R.string.all_home_edit_group)
      setView(binding.root)
      setPositiveButton(android.R.string.ok) { dialog, _ ->
        dialog.dismiss()
        onOkClickListener?.invoke(binding.etDialogEditGroupName.text.toString())
      }
      setNegativeButton(android.R.string.cancel, null)
      setNeutralButton(R.string.all_tab_del) { dialog, _ ->
        dialog.dismiss()
        onDelClickListener?.invoke()
      }
    }.show().apply {
      getButton(DialogInterface.BUTTON_NEUTRAL).setTextColor(Color.RED)
    }
  }
}
