package com.a00n.stars.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import com.a00n.stars.data.local.entities.Star


class StarListCallBack : DiffUtil.ItemCallback<Star>() {
    override fun areItemsTheSame(oldItem: Star, newItem: Star): Boolean {
//        return false
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Star, newItem: Star): Boolean {
        return false
//        return oldItem.id == newItem.id && oldItem.star == newItem.star && oldItem.img == newItem.img
    }
}
