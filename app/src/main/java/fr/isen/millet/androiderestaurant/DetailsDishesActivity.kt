package fr.isen.millet.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import fr.isen.millet.androiderestaurant.databinding.ActivityDetailsDishesBinding

class DetailsDishesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsDishesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsDishesBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.titleDetailsDishes.text = intent.extras?.getString("titleDetails") ?: "No Categorie title found"
        if (intent.extras?.getString("imageDetails") != null) {
            Picasso.get().load(intent.extras?.getString("imageDetails")).into(binding.imageViewDetails)
        }
    }

}