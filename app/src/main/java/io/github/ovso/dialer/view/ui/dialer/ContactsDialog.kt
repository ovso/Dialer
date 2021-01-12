package io.github.ovso.dialer.view.ui.dialer

import android.content.Context
import android.content.Intent
import android.provider.ContactsContract
import android.view.LayoutInflater
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AlertDialog
import androidx.core.database.getStringOrNull
import com.orhanobut.logger.Logger
import io.github.ovso.dialer.R
import io.github.ovso.dialer.data.view.ContactsDialogModel
import io.github.ovso.dialer.databinding.DialogDialerAddNoBinding

class ContactsDialog(
  private val context: Context,
  private val launcher: ActivityResultLauncher<Intent>
) {
  private var binding: DialogDialerAddNoBinding =
    DialogDialerAddNoBinding.inflate(LayoutInflater.from(context))

  var onOkClickListener: ((ContactsDialogModel) -> Unit)? = null
  var onCancelClickListener: ((ContactsDialogModel) -> Unit)? = null
  var color: String = context.resources.getStringArray(R.array.picker_colors)[0]
  var index = 0
  fun show(): ContactsDialog {
    binding.apply {

      pickerAddDialog.also { picker ->
        val colors = context.resources.getStringArray(R.array.picker_colors).toList()
        picker.colors = colors
        picker.onItemClickListener = { index, color ->
          Logger.d("onItemClick: $index, $color")
          this@ContactsDialog.color = color
          this@ContactsDialog.index = index
        }
      }
      tvAddDialogGetNo.setOnClickListener {
        Intent(Intent.ACTION_PICK).apply {
          data = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
          launcher.launch(this)
        }
      }
    }
    AlertDialog.Builder(context).apply {
      setView(binding.root)
      setCancelable(false)
      setPositiveButton(android.R.string.ok) { dialog, _ ->
        onOkClickListener?.invoke(
          ContactsDialogModel(
            nm = binding.etAddDialogNm.text.toString(),
            no = binding.etAddDialogNo.text.toString(),
            color = color
          ).apply {
            Logger.d("ContactsDialogModel: $this")
          }
        )
        dialog.dismiss()
      }
      setNegativeButton(android.R.string.cancel) { dialog, _ ->
        dialog.dismiss()
      }
      show()
    }

    return this
  }

  fun onContactsResult(it: ActivityResult?) {
    Logger.d("it: ${it?.data?.data?.path}")
    it?.data?.data?.let { uri ->
      context.contentResolver?.query(
        uri,
        arrayOf(
          ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
          ContactsContract.CommonDataKinds.Phone.NUMBER
        ),
        null, null, null
      )?.use { cursor ->
        cursor.moveToFirst()
        val nm = cursor.getStringOrNull(0)
        val no = cursor.getStringOrNull(1)
        Logger.d("nm: $nm")
        Logger.d("no: $no")
        binding.etAddDialogNm.setText(nm)
        binding.etAddDialogNo.setText(no)
      }
    }
  }
}
