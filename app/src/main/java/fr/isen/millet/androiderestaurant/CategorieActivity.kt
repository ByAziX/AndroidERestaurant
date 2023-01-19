package fr.isen.millet.androiderestaurant

import CustomAdapter
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
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
    var url = "http://test.api.catering.bluecodegames.com/menu"
    @SuppressLint("AppCompatMethod")
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


                val gson = Gson()
                val list: Data = gson.fromJson(it.toString(), Data::class.java)
                val filterList = list.data.filter { it.name_fr == binding.TitleCategorie.text }
            //get items from the list
                val items = filterList[0].items

                Log.d("filterList", list.toString())

                val recyclerView = binding.recyclerview
                recyclerView.layoutManager = LinearLayoutManager(this)
                recyclerView.adapter = CustomAdapter(filterList[0].items) {

                    val intent = Intent(this, DetailsDishesActivity::class.java)
                    intent.putExtra("titleDetails", it)
                    startActivity(intent)
                }




            },
             {

                Log.e("API Error", it.toString())
            })


        Volley.newRequestQueue(this).add(request)


       /* val recyclerView = binding.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(this)

        val dishes = when (binding.TitleCategorie.text) {
            Categorie.ENTREES.value -> resources.getStringArray(R.array.starter)
                .toList() as ArrayList<String>

            Categorie.PLATS.value -> resources.getStringArray(R.array.plats)
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
*/




    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d ("onDestroy", "$this onDestroy")
    }
}

// curl -X POST http://test.api.catering.bluecodegames.com/menu -d '{"id_shop":"1"}'