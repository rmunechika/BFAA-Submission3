package com.first.subgitfinalapp.Adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.first.subgitfinalapp.Activity_and_Fragment.GitUserDetailActivity
import com.first.subgitfinalapp.CustomOnItemClickListener
import com.first.subgitfinalapp.GitUser
import com.first.subgitfinalapp.R
import com.first.subgitfinalapp.databinding.ItemRowUserBinding

class ListGitUserFavAdapter(private val activity: Activity) : RecyclerView.Adapter<ListGitUserFavAdapter.ListViewHolder>() {
    var mData = ArrayList<GitUser>()
        set(value) {
            field = value
            if (value.size > 0) {
                this.mData.clear()
            }
            this.mData.addAll(value)
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
            itemView.setOnClickListener(CustomOnItemClickListener(adapterPosition, object : CustomOnItemClickListener.OnItemClickCallback{
                override fun onItemClicked(view: View, position: Int) {
                    val intent = Intent(activity, GitUserDetailActivity::class.java)
                    intent.putExtra(GitUserDetailActivity.EXTRA_POSITION, position)
                    intent.putExtra(GitUserDetailActivity.EXTRA_DATAFAV, gituser)
                    activity.startActivity(intent)
                }
            }))
        }
    }
}