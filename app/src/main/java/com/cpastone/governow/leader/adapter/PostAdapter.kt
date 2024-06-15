package com.cpastone.governow.leader.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cpastone.governow.leader.R
import com.cpastone.governow.leader.data.model.Post
import com.cpastone.governow.leader.ui.post.DetailPostActivity

class PostAdapter :
    PagingDataAdapter<Post, PostAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_story, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.iv_image_item)
        private val description: TextView = itemView.findViewById(R.id.tv_name_item)
        private val name: TextView = itemView.findViewById(R.id.tv_title_item)

        init {
            itemView.setOnClickListener {
                val story = getItem(bindingAdapterPosition)
                val intent = Intent(itemView.context, DetailPostActivity::class.java)
                intent.putExtra("name", story?.name)
                intent.putExtra("description", story?.description)
                intent.putExtra("photoUrl", story?.photoUrl)
                itemView.context.startActivity(intent)
            }
        }

        fun bind(story: Post) {
            Log.d("hohoyx", story.toString())
            Glide.with(imageView.context)
                .load(story.photoUrl)
                .placeholder(R.drawable.ic_baseline_account_box)
                .error(R.drawable.ic_baseline_account_box)
                .into(imageView)

            description.text = story.description
            name.text = story.name
        }
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Post>() {
            override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem == newItem
            }
        }
    }
}

