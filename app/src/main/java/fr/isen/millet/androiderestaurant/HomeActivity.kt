package fr.isen.millet.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import fr.isen.millet.androiderestaurant.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val buttonStarter = findViewById<Button>(R.id.StarterButton)
        //val buttonPlats = findViewById<Button>(R.id.platButton)
        //val buttonDesserts = findViewById<Button>(R.id.DessertButton)
        binding.EntreesButton .setOnClickListener{
            ChangePage(binding.EntreesButton)
        }
        binding.plastButton .setOnClickListener{
            ChangePage(binding.plastButton)
        }
        binding.DessertButton.setOnClickListener{
            ChangePage(binding.DessertButton)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d ("onDestroy", "$this onDestroy")
    }

fun ChangePage(Button: Button) {
    val intent = Intent(this, CategorieActivity::class.java)
    Toast.makeText(this@HomeActivity,Button.text,Toast.LENGTH_SHORT).show()
    intent.putExtra("TitleCategorie",Button.text)
    startActivity(intent)
}


}
