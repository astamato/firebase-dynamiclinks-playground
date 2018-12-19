package com.tvptdigital.android.sessions.myrecipes.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tvptdigital.android.sessions.myrecipes.R
import com.tvptdigital.android.sessions.myrecipes.detail.RecipeDetailActivity
import com.tvptdigital.android.sessions.myrecipes.list.model.Recipe
import kotlinx.android.synthetic.main.recipe_list_item.view.*

class RecipeAdapter(private val items: ArrayList<Recipe>, private val context: Context) : androidx.recyclerview.widget.RecyclerView.Adapter<ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = items[position].name
        holder.difficulty.text = items[position].difficulty.name

        holder.pic.setImageResource(items[position].picSmall)

        holder.itemView.setOnClickListener {
            val intent = RecipeDetailActivity.newIntent(context, items[position])
            context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.recipe_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class ViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    var name = view.name
    val difficulty = view.difficulty
    val pic = view.pic
}