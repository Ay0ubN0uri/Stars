package com.a00n.stars.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.a00n.stars.data.local.entities.Star

class MainViewModel : ViewModel() {
    private val stars = mutableListOf<Star>()

    val s = MutableLiveData<List<Star>>()

    fun findStar(id: Int): Star? {
        return stars.find { it.id == id }
    }

    fun findAll(): List<Star> = stars.toList()
    fun add(star: Star) = stars.add(star)
    fun delete(id: Int): Boolean {
        val starToRemove = findStar(id)
        return if (starToRemove != null) {
            stars.remove(starToRemove)
            true
        } else {
            false
        }
    }

    fun update(id: Int, name: String, img: String, star: Int): Boolean {
        val starToUpdate = findStar(id)
        return if (starToUpdate != null) {
            starToUpdate.name = name
            starToUpdate.img = img
            starToUpdate.star = star
            true
        } else {
            false
        }
    }


}