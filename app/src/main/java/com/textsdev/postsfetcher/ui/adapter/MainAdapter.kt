package com.textsdev.postsfetcher.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.textsdev.postsfetcher.databinding.PostsItemLayoutBinding
import com.textsdev.postsfetcher.model.PostsModel

class MainAdapter(private val onClick: (PostsModel) -> Unit) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    private val data = mutableListOf<PostsModel>()

    fun addData(newData: List<PostsModel>) {
        data.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(PostsItemLayoutBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])

        holder.itemView.setOnClickListener {
            onClick(data[position])
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(itemView: PostsItemLayoutBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        private val titleTv: TextView = itemView.titleTv
        private val bodyTv: TextView = itemView.bodyTv
        private val userId: TextView = itemView.userId
        private val postId: TextView = itemView.postId

        init {
            itemView.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }

        fun bind(item: PostsModel) {
            titleTv.text = item.title
            bodyTv.text = item.body
            userId.text = "User id - ${item.userId}"
            postId.text = "Post id - ${item.id}"
        }
    }
}