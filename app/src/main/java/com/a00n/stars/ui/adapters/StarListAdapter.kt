package com.a00n.stars.ui.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.a00n.stars.R
import com.a00n.stars.data.local.entities.Star
import com.a00n.stars.databinding.StarEditItemBinding
import com.a00n.stars.databinding.StarItemBinding
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import kotlin.math.ceil
import kotlin.math.round

class StarListAdapter : ListAdapter<Star, StarListAdapter.StarViewHolder>(StarListCallBack()),
    Filterable {

    private var starListFull: List<Star> = emptyList()

    fun setFullList(list: List<Star>) {
        starListFull = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StarViewHolder {
        val binding = StarItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return StarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StarViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem, holder.itemView.context)
    }

    inner class StarViewHolder(private val binding: StarItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(star: Star, context: Context) {
            val listStars = listOf<Int>(
                R.id.star1,
                R.id.star2,
                R.id.star3,
                R.id.star4,
                R.id.star5,
            )
            val listLottieStars = listOf<LottieAnimationView>(
                binding.star1,
                binding.star2,
                binding.star3,
                binding.star4,
                binding.star5,
            )
            listLottieStars.forEach { lottie->
                lottie.visibility = View.VISIBLE
                lottie.cancelAnimation()
                lottie.playAnimation()
            }
            for (i in star.star..4) {
                binding.root.findViewById<LottieAnimationView>(listStars[i]).visibility = View.GONE
            }
            binding.star = star
            binding.listStars = listStars
            binding.starConstraintLayout.setOnClickListener {
                val popup =
                    LayoutInflater.from(context).inflate(R.layout.star_edit_item, null, false)
                val binding = StarEditItemBinding.inflate(LayoutInflater.from(context))
                popup.findViewById<TextView>(R.id.starTitleTextView).text = star.name
                val profileImage = popup.findViewById<CircleImageView>(R.id.starEditProfileImage)
                val ratingBar = popup.findViewById<RatingBar>(R.id.ratingBar)
                Glide.with(popup)
                    .load(star.img)
                    .placeholder(R.drawable.ayoub_nouri)
                    .into(profileImage)
//                binding.starTitleTextView.text = star.name
//                binding.star = star
                val dialog = AlertDialog.Builder(context)
                    .setTitle("Rating")
                    .setMessage("Rate this person")
                    .setView(popup)
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Rate") { _, _ ->
                        val list = mutableListOf<Star>()
                        list.addAll(starListFull)
//                        list.add(Star("Ayoub Nouri", "", 1))
                        Log.i("info", "bind: ${ceil(ratingBar.rating).toInt()}")
                        list[star.id - 1].star = ceil(ratingBar.rating).toInt()
                        submitList(list)
                        Log.i("info", "bind: ${starListFull[star.id - 1]}")
                    }
                    .create()
                dialog.show()
            }
        }
    }

    override fun submitList(list: MutableList<Star>?) {
        Log.i("info", "submitList: $starListFull")
//        starListFull = list.orEmpty()
        Log.i("info", "submitList: $starListFull")
        super.submitList(list)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredResults = mutableListOf<Star>()
                if (constraint.isNullOrBlank()) {
                    filteredResults.addAll(starListFull)
                } else {
                    val filterPattern = constraint.toString().trim()
                    for (star in starListFull) {
                        if (star.name.contains(filterPattern, ignoreCase = true)) {
                            filteredResults.add(star)
                        }
                    }
                }
                val results = FilterResults()
                results.values = filteredResults
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                val filteredList = results?.values as MutableList<Star>
                Log.i("info", "publishResults: $filteredList")
                submitList(filteredList)
            }
        }
    }
}