package fr.isen.millet.androiderestaurant.pages

import CartAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import fr.isen.millet.androiderestaurant.databinding.ActivityCartBinding
import fr.isen.millet.androiderestaurant.datamodel.CartContainer
import java.io.File


class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var cartContainer: CartContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)


        readFromFile()

        val recyclerView = binding.recyclerviewCart
        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter =
            CartAdapter(arrayListOf()) { //title: String, price: String, image: String ->

                val intent = Intent(this, DetailsDishesActivity::class.java)
                intent.putExtra("Items", it)
                startActivity(intent)


            }

        val adapter = recyclerView.adapter as CartAdapter
        adapter.refreshList(cartContainer.cartItemsList)

    }
    private fun readFromFile() {
        binding.pBar.visibility = View.VISIBLE

        val jsonFile = File(filesDir, "cart.json")
        val jsonContent = jsonFile.readText()
        val gson = Gson()
        cartContainer = gson.fromJson(jsonContent, CartContainer::class.java)
        binding.pBar.visibility = View.GONE

    }
}