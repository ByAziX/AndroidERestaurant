package fr.isen.millet.androiderestaurant

import CustomAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.millet.androiderestaurant.databinding.ActivityCategorieBinding


class CategorieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategorieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategorieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val title = findViewById<TextView>(R.id.TitleCategorie)
        binding.TitleCategorie.text = intent.extras?.getString("TitleCategorie") ?: "No Categorie title found"

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


        when(binding.TitleCategorie.text){
            "Starter" -> {
                recyclerView.adapter = CustomAdapter(resources.getStringArray(R.array.starter))

            }
            "Plats" -> {
                recyclerView.adapter = CustomAdapter(resources.getStringArray(R.array.plats))
            }
            "Desserts" -> {
                recyclerView.adapter = CustomAdapter(resources.getStringArray(R.array.desserts))
            }
        }


    }

    fun GoToDetails(Button: Button) {
        val intent = Intent(this, InformationDishesActivity::class.java)
        Toast.makeText(this@CategorieActivity,Button.text, Toast.LENGTH_SHORT).show()
        intent.putExtra("titleDetails",Button.text)
        startActivity(intent)

    }



}