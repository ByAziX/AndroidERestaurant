package fr.isen.millet.androiderestaurant.pages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.millet.androiderestaurant.databinding.ActivityCartBinding


class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}