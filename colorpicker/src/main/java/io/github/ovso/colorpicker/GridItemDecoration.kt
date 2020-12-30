package io.github.ovso.colorpicker

import android.graphics.Rect
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class GridItemDecoration(displayMetrics: DisplayMetrics) : ItemDecoration() {
  private val spanCount = 5
  private val spacing: Int
  private val outerMargin: Int
  override fun getItemOffsets(
    outRect: Rect,
    view: View,
    parent: RecyclerView,
    state: RecyclerView.State
  ) {
    val maxCount = parent.adapter!!.itemCount
    val position = parent.getChildAdapterPosition(view)
    val column = position % spanCount
    val row = position / spanCount
    val lastRow = (maxCount - 1) / spanCount
    outRect.left = column * spacing / spanCount
    outRect.right = spacing - (column + 1) * spacing / spanCount
    outRect.top = spacing * 1
    if (row == lastRow) {
      outRect.bottom = outerMargin
    }
  }

  init {
    spacing = TypedValue.applyDimension(
      TypedValue.COMPLEX_UNIT_DIP,
      12f,
      displayMetrics
    ).toInt()
    outerMargin = TypedValue.applyDimension(
      TypedValue.COMPLEX_UNIT_DIP,
      10f,
      displayMetrics
    ).toInt()
  }
}
