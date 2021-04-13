package com.first.consumerapp

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.first.consumerapp.databinding.ItemRowUserBinding

class ListGitUserFavAdapter(private val activity: Activity) : RecyclerView.Adapter<ListGitUserFavAdapter.ListViewHolder>() {
    val mData = ArrayList<GitUser>()

    fun setData(items: ArrayList<GitUser>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ListViewHolder {
        val mView = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_user, viewGroup, false)
        return ListViewHolder(mView)
    }

    override fun onBindViewHolder(listViewHolder: ListViewHolder, position: Int) {
        listViewHolder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRowUserBinding.bind(itemView)
        fun bind(gituser: GitUser) {
            binding.tvUsername.text = gituser.username
            Glide.with(itemView.context)
                .load(gituser.avatar)
                .apply(RequestOptions().override(55,55))
                .into(binding.imgItemAvatar)
        }
    }
}