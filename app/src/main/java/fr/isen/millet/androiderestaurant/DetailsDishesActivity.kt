package fr.isen.millet.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.millet.androiderestaurant.databinding.ActivityDetailsDishesBinding

class DetailsDishesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsDishesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsDishesBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.titleDetailsDishes.text = intent.extras?.getString("titleDetails") ?: "No Categorie title found"

    }

}