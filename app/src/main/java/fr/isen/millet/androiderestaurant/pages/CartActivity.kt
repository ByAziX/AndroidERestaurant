package fr.isen.millet.androiderestaurant.pages

import CartAdapter
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import fr.isen.millet.androiderestaurant.databinding.ActivityCartBinding
import fr.isen.millet.androiderestaurant.datamodel.CartContainer
import java.io.File


class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var cartContainer: CartContainer

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)


        readFromFile()

        if (cartContainer.cartItemsList.isNotEmpty()) {

        val recyclerView = binding.recyclerviewCart
        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter =
            CartAdapter(arrayListOf()) { cartItem ->

                val intent = Intent(this, DetailsDishesActivity::class.java)
                intent.putExtra("Items", cartItem)
                startActivity(intent)
            }

        val adapter = recyclerView.adapter as CartAdapter
        adapter.refreshList(cartContainer.cartItemsList)

        } else
            binding.TitleCart.text = "Votre panier est vide"


        calculateTotalPrice()


        binding.priceAllCartView.setOnClickListener {
            if (cartContainer.cartItemsList.isNotEmpty()) {
                deleteAllItems()
                Snackbar.make(it, "Votre panier a été vidé", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()

                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)

            }
        }

    }
    private fun readFromFile() {
        binding.pBar.visibility = View.VISIBLE

        val jsonFile = File(filesDir, "cart.json")
        cartContainer = if (jsonFile.exists()) {
            val json = jsonFile.readText()
            Gson().fromJson(json, CartContainer::class.java)
        } else {
            CartContainer(arrayListOf())
        }
        binding.pBar.visibility = View.GONE

    }


    @SuppressLint("SetTextI18n")
    private fun calculateTotalPrice() {
        var totalPrice = 0.0
        for (cartItem in cartContainer.cartItemsList) {
            totalPrice += cartItem.items.prices[0].price * cartItem.quantity
        }
        binding.priceAllCartView.text = "Buy For " +totalPrice.toString() + "€"
    }

    // function to delete all items in cart
    private fun deleteAllItems() {
        cartContainer.cartItemsList.clear()
        val jsonFile = File(filesDir, "cart.json")
        jsonFile.delete()
        val adapter = binding.recyclerviewCart.adapter as CartAdapter
        adapter.refreshList(cartContainer.cartItemsList)
        calculateTotalPrice()
    }


    fun deleteOneItem(view: View) {

        cartContainer.cartItemsList.removeAt(0)
        val adapter = binding.recyclerviewCart.adapter as CartAdapter
        adapter.refreshList(cartContainer.cartItemsList)
        calculateTotalPrice()
    }

}