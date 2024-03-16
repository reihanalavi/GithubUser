package com.reihanalavi.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.reihanalavi.githubuser.data.response.User
import com.reihanalavi.githubuser.databinding.UserItemsBinding

class UserAdapter: RecyclerView.Adapter<UserAdapter.MyViewHolder>() {
    private val listUser = ArrayList<User>()

    class MyViewHolder(val binding: UserItemsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            Glide
                .with(binding.root.context)
                .load(user.avatarUrl)
                .centerCrop()
                .into(binding.ivUser)

            binding.tvUser.text = user.login

            binding.root.setOnClickListener {
//                val intent = Intent(binding.root.context, MainActivity::class.java)
//                intent.putExtra("ID", user.id)
//                binding.root.context.startActivity(intent)
                Toast.makeText(binding.root.context, user.id, LENGTH_SHORT).show()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = UserItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = listUser[position]
        holder.bind(user)
    }

    fun submitList(users: List<User>) {
        listUser.clear()
        listUser.addAll(users)
        notifyDataSetChanged()
    }

}