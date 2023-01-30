package fr.isen.millet.androiderestaurant.pages

import CustomAdapter
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import fr.isen.millet.androiderestaurant.R
import fr.isen.millet.androiderestaurant.databinding.ActivityCategorieBinding
import fr.isen.millet.androiderestaurant.datamodel.CartContainer
import fr.isen.millet.androiderestaurant.datamodel.Data
import org.json.JSONObject
import java.io.File


enum class Categorie(val value: String) {
    ENTREES("Entrées"),
    PLATS("Plats"),
    DESSERT("Desserts")
}

class CategorieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategorieBinding
    private var url = "http://test.api.catering.bluecodegames.com/menu"
    private lateinit var cartContainer: CartContainer
    private var textCartItemCount: TextView? = null
    private var absTopSubMenus: Menu? = null

    // function to create the menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        absTopSubMenus = menu
        menuInflater.inflate(R.menu.main_menu, menu)
        textCartItemCount = menu?.findItem(R.id.action_cart)?.actionView?.findViewById(R.id.cart_badge)
        val cartItem = menu?.findItem(R.id.action_cart)
        val cartView = cartItem?.actionView

        val backArrowItem = menu?.findItem(androidx.appcompat.R.id.home)
        val backArrowView = backArrowItem?.actionView

        backArrowView?.setOnClickListener {
            onOptionsItemSelected(backArrowItem)
        }


        setupBadge()

        cartView?.setOnClickListener {
                onOptionsItemSelected(cartItem)


        }
        return true
    }
    // function to handle the click on the menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_cart -> {
                    val intent = Intent(this, CartActivity::class.java)
                    startActivity(intent)
            }
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    //function to update the number of items in the cart
    private fun setupBadge() {

        if (File(filesDir, "cart.json").exists()) {
            readFromFile()

            textCartItemCount?.text = cartContainer.cartItemsList.size.toString()
        }
        else
            textCartItemCount?.text = "0"
    }

    // function to read the cart file
    @SuppressLint("AppCompatMethod", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategorieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        binding.pBar.visibility = View.VISIBLE
        binding.TitleCategorie.text =
            intent.extras?.getString("TitleCategorie") ?: "No Categorie title found"

        title = binding.TitleCategorie.text


        readFromFile()

        val recyclerView = binding.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = CustomAdapter(arrayListOf()) {

            val intent = Intent(this, DetailsDishesActivity::class.java)
            intent.putExtra("Items", it)
            startActivity(intent)

        }
        val json = JSONObject()
        json.put("id_shop", "1")

        val request = JsonObjectRequest(
            Request.Method.POST, url, json,
            {


                val gson = Gson()
                val list: Data = gson.fromJson(it.toString(), Data::class.java)
                val filterList = list.data.firstOrNull() { it.name_fr == binding.TitleCategorie.text }
                //get items from the list
                binding.pBar.visibility = View.GONE

                val adapter = binding.recyclerview.adapter as CustomAdapter
                if (filterList != null) {
                    adapter.refreshList(filterList.items)
                }

            },
             {

                Log.e("API Error", it.toString())
            })


        Volley.newRequestQueue(this).add(request)

    }
    // function to read the cart file
    override fun onDestroy() {
        super.onDestroy()
        Log.d ("onDestroy", "$this onDestroy")
    }


    // fonction to read the cart file
    private fun readFromFile() {
        val jsonFile = File(filesDir, "cart.json")
        cartContainer = if (jsonFile.exists()) {
            val json = jsonFile.readText()
            Gson().fromJson(json, CartContainer::class.java)
        } else {
            CartContainer(arrayListOf())
        }
    }

}
