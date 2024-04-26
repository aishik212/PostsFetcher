package com.textsdev.postsfetcher.ui.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.textsdev.postsfetcher.databinding.BottomSheetLayoutBinding
import com.textsdev.postsfetcher.databinding.CommentsRowBinding
import com.textsdev.postsfetcher.model.CommentsModel

class BottomSheet : BottomSheetDialogFragment() {
    companion object {
        private const val ARG_TITLE = "title"
        private const val ARG_DESCRIPTION = "description"

        var commentsList = listOf<CommentsModel.CommentsModelItem>()

        fun newInstance(
            title: String, description: String, comments: List<CommentsModel.CommentsModelItem>
        ): BottomSheet {
            val fragment = BottomSheet()
            val args = Bundle().apply {
                commentsList = comments
                putString(ARG_TITLE, title)
                putString(ARG_DESCRIPTION, description)
            }
            fragment.arguments = args
            return fragment
        }
    }

    lateinit var inflate: BottomSheetLayoutBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        inflate = BottomSheetLayoutBinding.inflate(inflater)
        return inflate.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.apply {
            inflate.titleTv.text = getString(ARG_TITLE)
            inflate.bodyTv.text = getString(ARG_DESCRIPTION)
            commentsList.forEach {
                val inflate1 = CommentsRowBinding.inflate(layoutInflater)
                inflate1.nameTv.text = it.name
                inflate1.emailTv.text = it.email
                inflate1.commentTv.text = it.body
                inflate1.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
                )
                inflate.commentsList.addView(inflate1.root)
            }
        }
    }
}