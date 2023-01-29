package fr.isen.millet.androiderestaurant.pages

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import fr.isen.millet.androiderestaurant.R
import fr.isen.millet.androiderestaurant.databinding.ActivityHomeBinding
import fr.isen.millet.androiderestaurant.datamodel.CartContainer
import java.io.File


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var cartContainer: CartContainer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Home"



        binding.EntreesButton.setOnClickListener{
            changePage(binding.EntreesButton)
        }
        binding.plastButton.setOnClickListener{
            changePage(binding.plastButton)
        }
        binding.DessertButton.setOnClickListener{
            changePage(binding.DessertButton)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d ("onDestroy", "$this onDestroy")
    }

private fun changePage(Button: Button) {
    val intent = Intent(this, CategorieActivity::class.java)
    Toast.makeText(this@HomeActivity,Button.text,Toast.LENGTH_SHORT).show()
    intent.putExtra("TitleCategorie",Button.text)
    startActivity(intent)
}
}
