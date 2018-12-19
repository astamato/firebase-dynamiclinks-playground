package com.tvptdigital.android.sessions.myrecipes.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.tvptdigital.android.sessions.myrecipes.R
import com.tvptdigital.android.sessions.myrecipes.list.model.Recipe
import kotlinx.android.synthetic.main.activity_recipe_detail.*
import kotlinx.android.synthetic.main.content_recipe_detail.*


class RecipeDetailActivity : AppCompatActivity() {
  companion object {
    private const val RECIPE_ID = "recipe_id"

    fun newIntent(context: Context, recipe: Recipe): Intent {
      val intent = Intent(context, RecipeDetailActivity::class.java)
      intent.putExtra(RECIPE_ID, recipe.id)
      return intent
    }
  }

  private var recipeId = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_recipe_detail)
    setSupportActionBar(toolbar)

    val handler = Handler()
    FirebaseDynamicLinks.getInstance()
        .getDynamicLink(intent)
        .addOnSuccessListener(this) { pendingDynamicLinkData ->
          val deepLink: Uri?
          if (pendingDynamicLinkData != null) {
            deepLink = pendingDynamicLinkData.link

            recipeId = deepLink?.getQueryParameter("id")!!.toInt()
            Log.d("RecipeDetailActivityTAG", recipeId.toString())
            handler.post { updateView(recipeId) }
          }
        }
        .addOnFailureListener(this
        ) { e -> Log.w("RecipeDetailActivityTAG", "getDynamicLink:onFailure", e) }

    recipeId = intent.getIntExtra(RECIPE_ID, 0)

    updateView(recipeId)

    fab.setOnClickListener { view ->
      val link = FirebaseDynamicLinks.getInstance().createDynamicLink()
          .setLink(Uri.parse("https://www.myrecipes.com/?id=$recipeId"))
          //the domain has to be aligned with google services file and the firebase console
          .setDomainUriPrefix("https://myrecipesandroidsessions.page.link")
          .setAndroidParameters(DynamicLink.AndroidParameters.Builder().build())
          // we can chose to create async a short link
          // we can choose to create a long one and then call to shorten
          .buildDynamicLink()


      val builder = AlertDialog.Builder(this)
      builder.setTitle("Link Created!")
      builder.setMessage(link.uri.toString())
      builder.setPositiveButton("OKEY!") { dialog, _ ->
        dialog.dismiss()
      }
      val dialog: AlertDialog = builder.create()
      dialog.show()
    }
  }

  private fun updateView(recipeId: Int) {
    //you get details and actual recipe data from db or any repository
    when (recipeId) {
      1 -> {
        recipeName.text = "I am definitely recipe 1"
        title = "Curried Coconut Quinoa"
      }
      2 -> {
        recipeName.text = "I am definitely recipe 2"
        title = "Chicken burrito"
      }
      3 -> {
        recipeName.text = "I am definitely recipe 3"
        title = "Marguerita Pizza"
      }
      4 -> {
        recipeName.text = "I am definitely recipe 4"
        title = "Sweet Potato & Black Bean Veggie Burgers"
      }
      else -> {
        recipeName.text = "Sorry nothing to see here, id undefined."
        title = getString(R.string.title_activity_recipe_detail)
      }
    }
  }

}
