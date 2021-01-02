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
import io.github.ovso.dialer.databinding.DialogDialerAddNoBinding

class ContactsDialog(
  private val context: Context,
  private val launcher: ActivityResultLauncher<Intent>
) {
  private var binding: DialogDialerAddNoBinding? =
    DialogDialerAddNoBinding.inflate(LayoutInflater.from(context))

  fun show(): ContactsDialog {
    binding?.apply {

      pickerAddDialog.also { picker ->
        picker.colors = context.resources.getStringArray(R.array.picker_colors).toList()
        picker.onItemClickListener = {
          Logger.d("index: $it")
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
      setView(binding?.root)
      setCancelable(false)
      setPositiveButton(android.R.string.ok) { dialog, _ ->
        binding = null
        dialog.dismiss()
      }
      setNegativeButton(android.R.string.cancel) { dialog, _ ->
        binding = null
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
        binding?.etAddDialogNm?.setText(nm)
        binding?.etAddDialogNo?.setText(no)
      }
    }

  }
}
