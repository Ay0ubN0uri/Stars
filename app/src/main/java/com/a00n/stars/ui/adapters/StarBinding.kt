package com.a00n.stars.ui.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import coil.load
import com.a00n.stars.R
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView


@SuppressLint("CheckResult")
@BindingAdapter("loadImageFromUrl")
fun CircleImageView.loadImage(imgUrl: String) {
//    Log.i("info", "loadImage: hello")
    Glide.with(this)
        .load(imgUrl)
        .placeholder(R.drawable.ayoub_nouri)
        .into(this)
//    this.load("https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fwww.themarysue.com%2Fwp-content%2Fuploads%2F2015%2F12%2Favatar.jpeg&f=1&nofb=1&ipt=3393a47b87bfb98c2df59085324f7479a10eac38fe79f4133ac2aab8fd3028cb&ipo=images") {
//        crossfade(600)
//        error(R.drawable.ayoub_nouri)
//    }
}


@BindingAdapter("showStars")
fun FrameLayout.showStars(star: Int){//, listStars: List<LottieAnimationView>) {
//    Log.i("info", "showStars: Hello from here")
    val listStars = listOf<Int>(
        R.id.star1,
        R.id.star2,
        R.id.star3,
        R.id.star4,
        R.id.star5,
    )
//    for (i in star..4) {
////        Log.i("info", "showStars: $i")
//        this.findViewById<LottieAnimationView>(listStars[i]).visibility = View.GONE
////        listStars[i - 1]?.visibility = View.GONE
//    }
//    Log.i("info", "showStars: done")

}