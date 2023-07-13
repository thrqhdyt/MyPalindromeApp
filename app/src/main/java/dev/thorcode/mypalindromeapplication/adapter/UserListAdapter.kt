package dev.thorcode.mypalindromeapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.thorcode.mypalindromeapplication.api.DataItem
import dev.thorcode.mypalindromeapplication.databinding.ItemRowUserBinding

class UserListAdapter(
    private inline val onItemClick: (String) -> Unit
) : PagingDataAdapter<DataItem, UserListAdapter.UserViewHolder>(DIFF_CALLBACK){

    class UserViewHolder(
        private val onItemClick: (String) -> Unit,
        private val binding: ItemRowUserBinding
        ): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataItem) {
            Glide.with(itemView.context)
                .load(data.avatar)
                .into(binding.avatarImg)
            binding.tvFirstName.text = data.firstName
            binding.tvLastName.text = data.lastName
            binding.tvEmail.text = data.email
            itemView.setOnClickListener {
                onItemClick("${data.firstName} ${data.lastName}")
            }
        }
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val data: DataItem? = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding = binding, onItemClick = onItemClick)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}