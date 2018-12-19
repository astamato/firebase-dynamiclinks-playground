package com.tvptdigital.android.sessions.myrecipes.list

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.tvptdigital.android.sessions.myrecipes.R
import com.tvptdigital.android.sessions.myrecipes.R.drawable
import com.tvptdigital.android.sessions.myrecipes.list.model.Recipe
import com.tvptdigital.android.sessions.myrecipes.list.model.RecipeDifficulty.*
import com.tvptdigital.android.sessions.myrecipes.list.model.RecipeType
import com.tvptdigital.android.sessions.myrecipes.list.model.RecipeType.VEGAN
import kotlinx.android.synthetic.main.activity_main.recipe_list
import kotlinx.android.synthetic.main.activity_recipe_detail.toolbar


class MainActivity : AppCompatActivity() {
    private val recipes: ArrayList<Recipe> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        addRecipes()

        processDynamicLink()

        // Creates a vertical Layout Manager
        recipe_list.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        // Access the RecyclerView Adapter and load the data into it
        recipe_list.adapter = RecipeAdapter(recipes, this)
        val itemDecor = androidx.recyclerview.widget.DividerItemDecoration(this, HORIZONTAL)
        recipe_list.addItemDecoration(itemDecor)

    }

    private fun processDynamicLink() {
        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(intent)
                .addOnSuccessListener(this) { pendingDynamicLinkData ->
                    // Get deep link from result (may be null if no link is found)
                    var deepLink: Uri? = null
                    if (pendingDynamicLinkData != null) {
                        deepLink = pendingDynamicLinkData.link
                        val option = deepLink.getQueryParameter("option")
                        Log.d("RecipeDetailActivityTAG", option)
                        if ("vegan" == option.toLowerCase()) {
                            //some logic of filtering
                            recipes.clear()
                            addVeganRecipes()
                            recipe_list.adapter?.notifyDataSetChanged()
                        }
                    }
                }
                .addOnFailureListener(this
                ) { e -> Log.w("MainActivityTAG", "getDynamicLink:onFailure", e) }
    }

    private fun addVeganRecipes() {
        val recipeVeganTypes = ArrayList<RecipeType>()
        recipeVeganTypes.add(VEGAN)
        val recipe1 = Recipe(1, "Curried Coconut Quinoa", recipeVeganTypes,
                EXPERT, "Step 1, 2, 3, 4", drawable.recipe_1)
        val recipe4 = Recipe(4, "Sweet Potato & Black Bean Veggie Burgers",
                recipeVeganTypes,
                NORMAL, "Step 1, 2, 3, 4", drawable.recipe_4)
        val recipe5 = Recipe(5, "Red Lentil Soup", recipeVeganTypes,
                HARD, "Step 1, 2, 3, 4", drawable.recipe_5)

        recipes.add(recipe1)
        recipes.add(recipe4)
        recipes.add(recipe5)
    }

    private fun addRecipes() {
        val recipeVeganTypes = ArrayList<RecipeType>()
        recipeVeganTypes.add(VEGAN)

        val recipe1 = Recipe(1, "Curried Coconut Quinoa", recipeVeganTypes,
                EXPERT, "Step 1, 2, 3, 4", drawable.recipe_1)

        val recipe2 = Recipe(2, "Chicken burrito", null,
                EASY, "Step 1, 2, 3, 4", drawable.recipe_2)

        val recipe3 = Recipe(3, "Marguerita Pizza", null,
                EASY, "Step 1, 2, 3, 4", drawable.recipe_3)

        val recipe4 = Recipe(4, "Sweet Potato & Black Bean Veggie Burgers",
                recipeVeganTypes,
                NORMAL, "Step 1, 2, 3, 4", drawable.recipe_4)
        val recipe5 = Recipe(5, "Red Lentil Soup", recipeVeganTypes,
                HARD, "Step 1, 2, 3, 4", drawable.recipe_5)

        recipes.add(recipe1)
        recipes.add(recipe2)
        recipes.add(recipe3)
        recipes.add(recipe4)
        recipes.add(recipe5)
    }
}
