package fr.isen.millet.androiderestaurant.pages

import CustomAdapter
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.millet.androiderestaurant.R
import fr.isen.millet.androiderestaurant.databinding.ActivityCategorieBinding
import fr.isen.millet.androiderestaurant.datamodel.Data
import org.json.JSONObject


enum class Categorie(val value: String) {
    ENTREES("Entrées"),
    PLATS("Plats"),
    DESSERT("Desserts")
}

class CategorieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategorieBinding
    private var url = "http://test.api.catering.bluecodegames.com/menu"

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        val cartItem=menu?.findItem(R.id.action_cart)
        val cartView=cartItem?.actionView

        val backArrowItem=menu?.findItem(androidx.appcompat.R.id.home)
        val backArrowView=backArrowItem?.actionView

        backArrowView?.setOnClickListener {
            onOptionsItemSelected(backArrowItem)
        }

        cartView?.setOnClickListener {
            onOptionsItemSelected(cartItem)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
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
    override fun onDestroy() {
        super.onDestroy()
        Log.d ("onDestroy", "$this onDestroy")
    }
}

// curl -X POST http://test.api.catering.bluecodegames.com/menu -d '{"id_shop":"1"}'