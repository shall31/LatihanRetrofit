package com.dicoding.picodiploma.latihanretrofit.ui.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.latihanretrofit.R
import com.dicoding.picodiploma.latihanretrofit.ui.detail.DetailActivity

class UsersAdapter(private val listUsers: List<ItemsItem>) : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_review, viewGroup, false))


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        //tempat untuk mengambil data dari listReview dan menyimpannya ke dalam tvItem
        viewHolder.tvItem.text = listUsers[position].login
        Glide.with(viewHolder.img).load(listUsers[position].avatarUrl).into(viewHolder.img)

        viewHolder.itemView.setOnClickListener{
            val intentDetail = Intent(viewHolder.itemView.context, DetailActivity::class.java)
            intentDetail.putExtra("key_users",listUsers[position].login)
            viewHolder.itemView.context.startActivity(intentDetail)
        }
    }

    override fun getItemCount() = listUsers.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //inisialisasi textview dan imageview
        val tvItem: TextView = view.findViewById(R.id.tv_games_name)
        val img: ImageView = view.findViewById(R.id.img_item_photo)

    }

}