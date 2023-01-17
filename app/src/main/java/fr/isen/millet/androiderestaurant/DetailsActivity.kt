package fr.isen.millet.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.millet.androiderestaurant.databinding.ActivityInformationDishesBinding

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInformationDishesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInformationDishesBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.titleDetails.text = intent.extras?.getString("titleDetails") ?: "No Categorie title found"
    }


}