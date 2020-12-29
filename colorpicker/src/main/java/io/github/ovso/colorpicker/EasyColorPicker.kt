package io.github.ovso.colorpicker

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.ColorRes
import io.github.ovso.colorpicker.databinding.ContainerBinding

class EasyColorPicker @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyle: Int = 0
) : FrameLayout(
  context,
  attrs,
  defStyle
) {

  private lateinit var view: View

  @ColorRes
  var color: Int? = null
    set(value) {
      field = value
      binding.root.setBackgroundColor(value!!)
    }

  init {
    init(attrs, defStyle)
  }

  private lateinit var binding: ContainerBinding
  private fun init(attrs: AttributeSet?, defStyle: Int) {
    binding = ContainerBinding.inflate(LayoutInflater.from(context), this, true)
    val typeArray = context.obtainStyledAttributes(
      attrs, R.styleable.EasyColorPicker, defStyle, 0
    )
    color = typeArray.getInt(R.styleable.EasyColorPicker_bgColor, 0x000000)
    binding.root.setBackgroundColor(color!!)
    typeArray.recycle()
  }

}
