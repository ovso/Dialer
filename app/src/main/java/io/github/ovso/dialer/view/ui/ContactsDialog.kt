package io.github.ovso.dialer.view.ui

import android.content.Intent
import android.provider.ContactsContract
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.database.getStringOrNull
import androidx.fragment.app.Fragment
import com.orhanobut.logger.Logger
import io.github.ovso.dialer.R
import io.github.ovso.dialer.databinding.DialogDialerAddNoBinding

class ContactsDialog(private val fragment: Fragment) {

  private var launcher: ActivityResultLauncher<Intent> =
    fragment.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
      Logger.d("it: ${it.data?.data?.path}")
      it.data?.data?.let { uri ->
        fragment.context?.contentResolver?.query(
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
  private var builder: AlertDialog.Builder? = null
  private var binding: DialogDialerAddNoBinding =
    DialogDialerAddNoBinding.inflate(fragment.layoutInflater)

  init {
    binding.pickerAddDialog.apply {
      colors = resources.getStringArray(R.array.picker_colors).toList()
      onItemClickListener = {
        Logger.d("index: $it")
      }
    }
    binding.tvAddDialogGetNo.setOnClickListener {
      Logger.d("onClick")
    }
    binding.tvAddDialogGetNo.setOnClickListener {
      Intent(Intent.ACTION_PICK).apply {
        data = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        launcher.launch(this)
      }
    }

    AlertDialog.Builder(fragment.requireContext()).apply {
      setView(binding.root)
      setCancelable(false)
      setPositiveButton(android.R.string.ok) { dialog, _ ->
        (binding.root.parent as? ViewGroup)?.removeView(binding.root)
        dialog.dismiss()
      }
      setNegativeButton(android.R.string.cancel) { dialog, _ ->
        (binding.root.parent as? ViewGroup)?.removeView(binding.root)
        dialog.dismiss()
      }
    }.also {
      builder = it
    }

  }

  fun show() {
    builder?.show()
  }
}
