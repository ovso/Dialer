package io.github.ovso.colorpicker

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.Coil
import io.github.ovso.colorpicker.databinding.ItemColorBinding

class ColorAdapter : ListAdapter<Any, ColorViewHolder>(DIFF_UTIL) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
    return ColorViewHolder.create(parent)
  }

  override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
    holder.onBindViewHolder(getItem(position))
  }

  companion object {
    val DIFF_UTIL = object : DiffUtil.ItemCallback<Any>() {
      override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        return oldItem == newItem
      }

      override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        return areItemsTheSame(oldItem, newItem)
      }
    }
  }

}


class ColorViewHolder private constructor(private val binding: ItemColorBinding) :
  RecyclerView.ViewHolder(binding.root) {

  fun onBindViewHolder(item: Any) {
    (item as? Int)?.let {
//      binding.ivColorItem.setColorFilter(item)
//      binding.ivColorItem.imageTintList = ColorStateList.valueOf(it)
    }
  }

  companion object {
    fun create(parent: ViewGroup): ColorViewHolder {
      return ColorViewHolder(
        ItemColorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      )
    }
  }
}
