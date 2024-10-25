package com.dicoding.picodiploma.latihanretrofit.ui.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.latihanretrofit.R
import com.dicoding.picodiploma.latihanretrofit.database.FavoriteUser
import com.dicoding.picodiploma.latihanretrofit.databinding.ItemReviewBinding
import com.dicoding.picodiploma.latihanretrofit.ui.detail.DetailActivity

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
    class ViewHolder (val binding: ItemReviewBinding)
        : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<FavoriteUser>(){
        override fun areItemsTheSame(oldItem: FavoriteUser, newItem: FavoriteUser): Boolean {
            return oldItem == newItem

        }

        override fun areContentsTheSame(oldItem: FavoriteUser, newItem: FavoriteUser): Boolean {
            return oldItem.username == newItem.username
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var listFavoriteUser: List<FavoriteUser>
        get() {
            return differ.currentList
        }
        set(value) {differ.submitList(value)}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapter.ViewHolder {
        return ViewHolder (
            ItemReviewBinding.bind(LayoutInflater.from(parent.context).inflate(R.layout.item_review, parent, false))
                )
    }

    override fun getItemCount(): Int {
        return listFavoriteUser.size
    }

    override fun onBindViewHolder(holder: FavoriteAdapter.ViewHolder, position: Int) {

        holder.binding.tvGamesName.text = listFavoriteUser[position].username
        Glide.with(holder.binding.root).load(listFavoriteUser[position].avatarUrl).into(holder.binding.imgItemPhoto)

        holder.itemView.setOnClickListener{
            val intentDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            intentDetail.putExtra("key_users", listFavoriteUser[position].username)
            holder.itemView.context.startActivity(intentDetail)
        }

    }
}