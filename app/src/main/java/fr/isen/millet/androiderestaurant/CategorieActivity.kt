package fr.isen.millet.androiderestaurant

import CustomAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import fr.isen.millet.androiderestaurant.databinding.ActivityCategorieBinding
import fr.isen.millet.androiderestaurant.datamodel.Dish
import org.json.JSONObject


enum class Categorie(val value: String) {
    STARTER("Starter"),
    MAIN("Plats"),
    DESSERT("Desserts")
}

class CategorieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategorieBinding
    var url = "http://test.api.catering.bluecodegames.com/menu"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategorieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val title = findViewById<TextView>(R.id.TitleCategorie)
        binding.TitleCategorie.text =
            intent.extras?.getString("TitleCategorie") ?: "No Categorie title found"

        actionBar?.title = binding.TitleCategorie.text



        val json = JSONObject()
        json.put("id_shop", "1")

        val request = JsonObjectRequest(
            Request.Method.POST, url, json,
            {
                Log.i("API SUCCESS", it.toString())
                // parse json to get dishes list and display it in a recycler view with a custom adapter (see below)
                Log.i("API oui",it.getJSONArray("data").toString())


            },
            Response.ErrorListener {

                Log.e("API Error", it.toString())
            })


        Volley.newRequestQueue(this).add(request)


        val recyclerView = binding.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(this)

        val dishes = when (binding.TitleCategorie.text) {
            Categorie.STARTER.value -> resources.getStringArray(R.array.starter)
                .toList() as ArrayList<String>

            Categorie.MAIN.value -> resources.getStringArray(R.array.plats)
                .toList() as ArrayList<String>

            Categorie.DESSERT.value -> resources.getStringArray(R.array.desserts)
                .toList() as ArrayList<String>
            else -> arrayListOf()
        }



        recyclerView.adapter = CustomAdapter(dishes) {
            val intent = Intent(this, DetailsDishesActivity::class.java)
            Toast.makeText(this@CategorieActivity, it, Toast.LENGTH_SHORT).show()
            intent.putExtra("titleDetails", it)
            startActivity(intent)
        }




    }
}

// curl -X POST http://test.api.catering.bluecodegames.com/menu -d '{"id_shop":"1"}'