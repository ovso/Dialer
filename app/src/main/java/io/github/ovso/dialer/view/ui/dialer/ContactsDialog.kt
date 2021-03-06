package io.github.ovso.dialer.view.ui.dialer

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.provider.ContactsContract
import android.view.LayoutInflater
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AlertDialog
import androidx.core.database.getStringOrNull
import androidx.core.widget.doOnTextChanged
import com.orhanobut.logger.Logger
import io.github.ovso.dialer.R
import io.github.ovso.dialer.data.args.ContactDialogArgs
import io.github.ovso.dialer.data.mapper.toContactDialogModel
import io.github.ovso.dialer.data.view.ContactsDialogModel
import io.github.ovso.dialer.databinding.DialogDialerAddNoBinding

class ContactsDialog(
  private val context: Context,
  private val launcher: ActivityResultLauncher<Intent>,
  private val args: ContactDialogArgs
) {
  private var binding: DialogDialerAddNoBinding =
    DialogDialerAddNoBinding.inflate(LayoutInflater.from(context))

  private var model: ContactsDialogModel = args.toContactDialogModel()

  var onOkClickListener: ((ContactsDialogModel) -> Unit)? = null
  var onDelClickListener: ((ContactsDialogModel) -> Unit)? = null
  var onCancelClickListener: ((ContactsDialogModel) -> Unit)? = null
  var colorPickerIndex = 0

  fun show(): ContactsDialog {
    binding.apply {
      pickerAddDialog.also { picker ->
        val colors = context.resources.getStringArray(R.array.picker_colors).toList()
        picker.checkIndex = colors.indexOf(model.color)
        model = model.copy(color = colors[picker.checkIndex])
        picker.colors = colors
        picker.onItemClickListener = { index, color ->
          model = model.copy(color = color)
          Logger.d("model: $model")
          this@ContactsDialog.colorPickerIndex = index
        }
      }
      tvAddDialogGetNo.setOnClickListener {
        Intent(Intent.ACTION_PICK).apply {
          data = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
          launcher.launch(this)
        }
      }
      tvAddDialogSms.setOnClickListener {
        val no = etAddDialogNo.text.toString()
        Intent(Intent.ACTION_SENDTO, Uri.parse("sms:$no")).apply {
          putExtra("sms_body", "")
          it.context.startActivity(this)
        }
      }

      etAddDialogNm.setText(model.nm)
      etAddDialogNo.setText(model.no)
      etAddDialogNm.doOnTextChanged { text, _, _, _ ->
        model = model.copy(nm = text.toString())
      }
      etAddDialogNo.doOnTextChanged { text, _, _, _ ->
        model = model.copy(no = text.toString())
      }
      Logger.d("model: $model")
    }
    AlertDialog.Builder(context).apply {
      setView(binding.root)
      setCancelable(false)
      setPositiveButton(android.R.string.ok) { dialog, _ ->
        onOkClickListener?.invoke(model)
        dialog.dismiss()
      }
      setNegativeButton(android.R.string.cancel) { dialog, _ ->
        dialog.dismiss()
      }
      if (Type.Update == args.type) {
        setNeutralButton(R.string.all_delete_contact) { dialog, _ ->
          dialog.dismiss()
          onDelClickListener?.invoke(model)
        }
      }
      show().apply {
        this.getButton(DialogInterface.BUTTON_NEUTRAL).setTextColor(Color.RED)
      }
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

  sealed class Type {
    object Update : Type() {
      override fun toString(): String = this::class.java.simpleName
    }

    object Insert : Type() {
      override fun toString(): String = this::class.java.simpleName
    }
  }
}
