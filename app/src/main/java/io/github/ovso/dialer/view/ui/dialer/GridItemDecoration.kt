package io.github.ovso.dialer.view.ui.dialer

import android.graphics.Rect
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class GridItemDecoration(displayMetrics: DisplayMetrics) : ItemDecoration() {
  private val spanCount = 3
  private val spacing: Int = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    10f,
    displayMetrics
  ).toInt()
  private val outerMargin: Int = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    0f,
    displayMetrics
  ).toInt()

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
    outRect.top = spacing * 2
    if (row == lastRow) {
      outRect.bottom = outerMargin
    }
  }

}
