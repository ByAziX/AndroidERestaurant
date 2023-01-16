package fr.isen.millet.androiderestaurant

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import fr.isen.millet.androiderestaurant.databinding.ActivityCategorieBinding


class CategorieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategorieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategorieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val title = findViewById<TextView>(R.id.TitleCategorie)
        binding.TitleCategorie.text = intent.extras?.getString("TitleCategorie") ?: "No Categorie title found"}



}