package io.github.ovso.colorpicker

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.ColorRes
import io.github.ovso.colorpicker.databinding.ViewEasyColorPickerBinding

class EasyColorPicker @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyle: Int = 0
) : FrameLayout(
  context,
  attrs,
  defStyle
) {

  private val binding by lazy {
    ViewEasyColorPickerBinding.inflate(LayoutInflater.from(context), this, true)
  }

  private val colorAdapter by lazy { ColorAdapter() }

  @ColorRes
  var color: Int? = null
    set(value) {
      field = value
      binding.root.setBackgroundColor(value!!)
    }

  var colors: Array<Int> = arrayOf(0, 1, )

  init {
    init(attrs, defStyle)
  }

  private fun init(attrs: AttributeSet?, defStyle: Int) {
    val typeArray = context.obtainStyledAttributes(
      attrs, R.styleable.EasyColorPicker, defStyle, 0
    )
    color = typeArray.getInt(R.styleable.EasyColorPicker_bgColor, 0x000000)
    binding.root.setBackgroundColor(color!!)
    binding.rv.adapter = colorAdapter
    colorAdapter.submitList(colors.toList())
    typeArray.recycle()
  }
}
