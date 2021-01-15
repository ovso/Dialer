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

  var onItemClickListener: ((Int, String) -> Unit)? = null
  var onItemReselectListener: ((Int, String) -> Unit)? = null

  private val binding by lazy {
    ViewEasyColorPickerBinding.inflate(LayoutInflater.from(context), this, true)
  }

  private val colorAdapter by lazy {
    ColorAdapter().apply {
      onItemClickListener = { index ->
        Log.d("colorAdapter", "index: $index")
        val newItems = items?.map {
          it.copy(check = false)
        }?.toMutableList()

        newItems?.get(index)?.copy(check = true)?.let {
          newItems.set(index, it)
        }
        items = newItems ?: emptyList()
        submitList(items)

        when (checkIndex) {
          index -> {
            this@EasyColorPicker.onItemReselectListener?.invoke(
              index,
              items!![index].color
            )
          }
          else -> {
            this@EasyColorPicker.onItemClickListener?.invoke(
              index,
              items!![index].color
            )
          }
        }

        checkIndex = index
      }
    }
  }

  @ColorRes
  var color: Int? = null
    set(value) {
      field = value
      binding.root.setBackgroundColor(value!!)
      Log.d("EasyColorPicker", "set color")
    }

  private var items: List<ColorModel>? = null

  var checkIndex: Int = 0
    set(value) {
      field = if (value > -1) value else 0
    }

  var colors: List<String>? = null
    set(value) {
      field = value
      items = value?.mapIndexed { index, color ->
        when (index == checkIndex) {
          true -> ColorModel(color = color, check = true)
          else -> ColorModel(color = color)
        }
      }
      colorAdapter.submitList(items)
    }

  init {
    init(attrs, defStyle)
  }

  private fun init(attrs: AttributeSet?, defStyle: Int) {
    val typeArray = context.obtainStyledAttributes(
      attrs, R.styleable.EasyColorPicker, defStyle, 0
    )
    binding.rv.apply {
      itemAnimator = null
      addItemDecoration(
        GridItemDecoration(
          context.resources.displayMetrics
        )
      )
      adapter = colorAdapter
    }
    if (items.isNullOrEmpty().not()) {
      colorAdapter.submitList(items)
    }
    typeArray.recycle()
  }
}
