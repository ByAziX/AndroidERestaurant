package fr.isen.millet.androiderestaurant.pages

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import fr.isen.millet.androiderestaurant.R
import fr.isen.millet.androiderestaurant.databinding.ActivityHomeBinding
import fr.isen.millet.androiderestaurant.datamodel.CartContainer
import java.io.File

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var cartContainer: CartContainer
    private var textCartItemCount: TextView? = null

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        textCartItemCount = menu?.findItem(R.id.action_cart)?.actionView?.findViewById(R.id.cart_badge)
        val cartItem = menu?.findItem(R.id.action_cart)
        val cartView = cartItem?.actionView

        val backArrowItem = menu?.findItem(androidx.appcompat.R.id.home)
        val backArrowView = backArrowItem?.actionView

        backArrowView?.setOnClickListener {
            onOptionsItemSelected(backArrowItem)
        }

        cartView?.setOnClickListener {
            onOptionsItemSelected(cartItem)
        }
        setupBadge()
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_cart -> {
                val intent = Intent(this, CartActivity::class.java)
                startActivity(intent)
            }
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupBadge() {

        val count = cartContainer.cartItemsList.size


        if (textCartItemCount != null) {
            if (count === 0) {
                if (textCartItemCount?.visibility !== View.GONE) {
                    textCartItemCount?.visibility = View.GONE
                }
            } else {
                textCartItemCount?.text = java.lang.String.valueOf(count.coerceAtMost(99))
                if (textCartItemCount!!.getVisibility() !== View.VISIBLE) {
                    textCartItemCount?.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Home"

        readFromFile()

        //val buttonStarter = findViewById<Button>(R.id.StarterButton)
        //val buttonPlats = findViewById<Button>(R.id.platButton)
        //val buttonDesserts = findViewById<Button>(R.id.DessertButton)
        binding.EntreesButton.setOnClickListener{
            ChangePage(binding.EntreesButton)
        }
        binding.plastButton.setOnClickListener{
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

private fun ChangePage(Button: Button) {
    val intent = Intent(this, CategorieActivity::class.java)
    Toast.makeText(this@HomeActivity,Button.text,Toast.LENGTH_SHORT).show()
    intent.putExtra("TitleCategorie",Button.text)
    startActivity(intent)
}


    private fun readFromFile() {
        val jsonFile = File(filesDir, "cart.json")
        val jsonContent = jsonFile.readText()
        val gson = Gson()
        cartContainer = gson.fromJson(jsonContent, CartContainer::class.java)
    }

}
