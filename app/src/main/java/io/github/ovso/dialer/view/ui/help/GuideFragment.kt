package io.github.ovso.dialer.view.ui.help

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.github.ovso.dialer.R
import io.github.ovso.dialer.databinding.FragmentGuideBinding

class GuideFragment private constructor() : BottomSheetDialogFragment() {

  private lateinit var binding: FragmentGuideBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    return FragmentGuideBinding.inflate(inflater, container, false).also {
      binding = it
    }.root
  }

  companion object {
    fun newInstance(): GuideFragment {
      return GuideFragment().apply { }
    }
  }
}
