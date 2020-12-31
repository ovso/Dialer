package io.github.ovso.colorpicker

import android.content.Context
import android.util.AttributeSet
import android.util.Log
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
      Log.d("EasyColorPicker", "set color")
    }

  var colors: List<String>? = null
    set(value) {
      field = value
      colorAdapter.submitList(colors?.toList())
//      requestLayout()
    }

  init {
    init(attrs, defStyle)
  }

  private fun init(attrs: AttributeSet?, defStyle: Int) {
    val typeArray = context.obtainStyledAttributes(
      attrs, R.styleable.EasyColorPicker, defStyle, 0
    )
//    color = typeArray.getInt(R.styleable.EasyColorPicker_colors, 0x000000)

//    binding.root.setBackgroundColor(color!!)
    Log.d("EasyColorPicker", "init")
    binding.rv.addItemDecoration(
      GridItemDecoration(
        context.resources.displayMetrics
      )
    )
    binding.rv.adapter = colorAdapter
    colors?.count()?.let {
      colorAdapter.submitList(colors?.toList())
    }
    typeArray.recycle()
  }
}
