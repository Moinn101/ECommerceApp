package com.example.ecommerceapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerceapp.model.ColorAttribute
import com.moin.ecommerceapp.R

class ColorAdapter(private val colors: List<ColorAttribute>) : RecyclerView.Adapter<ColorAdapter.ColorViewHolder>() {
    class ColorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val colorImage: ImageView = itemView.findViewById(R.id.colorImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_color, parent, false)
        return ColorViewHolder(view)
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        val color = colors[position]
        Glide.with(holder.itemView.context)
            .load(color.swatch_url)
            .centerCrop()
            .into(holder.colorImage)
    }

    override fun getItemCount(): Int = colors.size
}