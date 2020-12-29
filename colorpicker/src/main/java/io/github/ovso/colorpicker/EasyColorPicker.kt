package io.github.ovso.colorpicker

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
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


  var color: Int? = null
    set(value) {
      field = value
      invalidate()
      requestLayout()
    }

  init {
    init(attrs, defStyle)
  }

  private lateinit var binding: ContainerBinding
  private fun init(attrs: AttributeSet?, defStyle: Int) {
    // Load attributes
    binding = ContainerBinding.inflate(LayoutInflater.from(context), this, false)
    val typeArray = context.obtainStyledAttributes(
      attrs, R.styleable.EasyColorPicker, defStyle, 0
    )
    color = typeArray.getInt(R.styleable.EasyColorPicker_bgColor, 0x000000)


    addView(binding.root, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    binding.root.setBackgroundColor(color!!)

    typeArray.recycle()

  }

  fun setBg(bg: Int) {
    binding.root.setBackgroundColor(bg)
  }

}
