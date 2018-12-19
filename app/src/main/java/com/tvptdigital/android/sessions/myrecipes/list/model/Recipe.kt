package com.tvptdigital.android.sessions.myrecipes.list.model

import androidx.annotation.DrawableRes

data class Recipe(val id: Int, val name: String, val types: ArrayList<RecipeType>?,
    val difficulty: RecipeDifficulty, val steps: String, @DrawableRes val picSmall: Int)

enum class RecipeType(val value: String) {
  VEGAN("Vegan"), VEGETARIAN("Vegetarian"), GLUTEN_FREE("Gluten free")
}

enum class RecipeDifficulty(val difficulty: String) {
  AMATEUR("Amateur"), EASY("Easy"), NORMAL("Normal"), HARD("Hard"), EXPERT("Expert")
}