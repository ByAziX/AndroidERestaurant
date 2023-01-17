package fr.isen.millet.androiderestaurant

import CustomAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.millet.androiderestaurant.databinding.ActivityCategorieBinding

enum class Categorie(val value: String) {
    STARTER("Starter"),
    MAIN("Plats"),
    DESSERT("Desserts")
}

class CategorieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategorieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategorieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val title = findViewById<TextView>(R.id.TitleCategorie)
        binding.TitleCategorie.text =
            intent.extras?.getString("TitleCategorie") ?: "No Categorie title found"

        actionBar?.title = binding.TitleCategorie.text


        /* binding.recyclerview.layoutManager = LinearLayoutManager(this)
        val data = ArrayList<ItemsViewModel>()
        for (i in 1..20) {

            // add value to the list of data to be displayed in recyclerview adapter from string.xml file
            data.add(ItemsViewModel(R.drawable.ic_launcher_foreground,"item"+i ))
        }
        val adapter = CustomAdapter(data)
        binding.recyclerview.adapter = adapter*/

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
            val intent = Intent(this, DetailsDishes::class.java)
            Toast.makeText(this@CategorieActivity, it, Toast.LENGTH_SHORT).show()
            intent.putExtra("titleDetails", it)
            startActivity(intent)
        }

    }

}