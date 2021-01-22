package io.github.ovso.dialer.view.ui.dialer.adapter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.ovso.dialer.data.view.DialerItemModel
import io.github.ovso.dialer.databinding.ItemDialerBinding
import io.github.ovso.dialer.databinding.ItemDialerFooterBinding

class DialerAdapter : ListAdapter<DialerItemModel, RecyclerView.ViewHolder>(diffCallback) {

  var itemClickListener: ((DialerItemModel) -> Unit)? = null

  companion object {
    val diffCallback = object : DiffUtil.ItemCallback<DialerItemModel>() {
      override fun areItemsTheSame(oldItem: DialerItemModel, newItem: DialerItemModel): Boolean =
        oldItem == newItem

      override fun areContentsTheSame(oldItem: DialerItemModel, newItem: DialerItemModel): Boolean =
        oldItem.no == newItem.no
    }
    private const val VIEW_TYPE_FOOTER = -1
    private const val VIEW_TYPE_ITEM = 0
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
    when (viewType == VIEW_TYPE_FOOTER) {
      true -> DialerFooterViewHolder.create(parent).apply {
        this.clickListener = itemClickListener
      }
      else -> DialerViewHolder.create(parent).apply {
        this.clickListener = itemClickListener
      }
    }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    @Suppress("UNCHECKED_CAST")
    (holder as OnBind<DialerItemModel>).onBindViewHolder(getItem(position))
  }

  override fun getItemViewType(position: Int): Int =
    when (getItem(position).footer) {
      true -> VIEW_TYPE_FOOTER
      else -> VIEW_TYPE_ITEM
    }
}

class DialerViewHolder private constructor(
  private val binding: ItemDialerBinding
) : RecyclerView.ViewHolder(binding.root), OnBind<DialerItemModel> {

  var clickListener: ((DialerItemModel) -> Unit)? = null

  override fun onBindViewHolder(item: DialerItemModel) {
    binding.apply {
      tvDialerItemName.text = item.name
      ivDialerItemColor.setImageDrawable(ColorDrawable(Color.parseColor(item.color)))
      root.apply {
        setOnClickListener {
          clickListener?.invoke(item)
        }
        setOnLongClickListener {
          Toast.makeText(context, "통화하기", Toast.LENGTH_SHORT).show()
          true
        }
      }
    }
  }

  companion object {
    fun create(parent: ViewGroup): DialerViewHolder {
      return DialerViewHolder(
        ItemDialerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      )
    }
  }
}

class DialerFooterViewHolder private constructor(
  private val binding: ItemDialerFooterBinding
) : RecyclerView.ViewHolder(binding.root), OnBind<DialerItemModel> {

  var clickListener: ((DialerItemModel) -> Unit)? = null

  override fun onBindViewHolder(item: DialerItemModel) {
    binding.root.setOnClickListener {
      clickListener?.invoke(item)
    }
  }

  companion object {
    fun create(parent: ViewGroup): DialerFooterViewHolder {
      return DialerFooterViewHolder(
        ItemDialerFooterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      )
    }

  }
}

interface OnBind<T> {
  fun onBindViewHolder(item: T)
}
