package io.github.ovso.colorpicker

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import io.github.ovso.colorpicker.databinding.ItemColorBinding

class ColorAdapter : ListAdapter<ColorModel, ColorViewHolder>(DIFF_UTIL) {

  var onItemClickListener: ((Int) -> Unit)? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
    return ColorViewHolder.create(parent)
  }

  override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
    holder.apply {
      onBindViewHolder(getItem(position))
      itemView.setOnClickListener {
        onItemClickListener?.invoke(position)
      }
    }
  }

  companion object {
    val DIFF_UTIL = object : DiffUtil.ItemCallback<ColorModel>() {
      override fun areItemsTheSame(oldItem: ColorModel, newItem: ColorModel): Boolean {
        return oldItem.color == newItem.color
      }

      override fun areContentsTheSame(oldItem: ColorModel, newItem: ColorModel): Boolean {
        return oldItem.check == newItem.check
      }
    }
  }
}

class ColorViewHolder private constructor(private val binding: ItemColorBinding) :
  RecyclerView.ViewHolder(binding.root) {
  fun onBindViewHolder(item: ColorModel) {
    Log.d("onBindViewHolder", item.toString())
    binding.apply {
      ivColorItem.setImageDrawable(ColorDrawable(Color.parseColor(item.color)))
      ivItemCheck.isVisible = item.check
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
