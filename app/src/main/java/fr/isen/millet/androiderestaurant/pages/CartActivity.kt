package fr.isen.millet.androiderestaurant.pages

import CartAdapter
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import fr.isen.millet.androiderestaurant.R
import fr.isen.millet.androiderestaurant.databinding.ActivityCartBinding
import fr.isen.millet.androiderestaurant.datamodel.CartContainer
import java.io.File


class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var cartContainer: CartContainer

    // fonction qui permet de créer le menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val backArrowItem = menu?.findItem(androidx.appcompat.R.id.home)
        val backArrowView = backArrowItem?.actionView

        backArrowView?.setOnClickListener {
            onOptionsItemSelected(backArrowItem)
        }

        return true
    }
    // fonction qui permet de gérer les actions du menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // fonction qui permet de créer l'activité
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Your Cart"
        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        readFromFile()
        binding.TitleCart.text = "${cartContainer.cartItemsList.size} items dans ton cart"

        recyclerViewRefresh()

        calculateTotalPrice()

        binding.priceAllCartView.setOnClickListener {
            if (cartContainer.cartItemsList.isNotEmpty()) {
                Snackbar.make(it, "Prix total : ${binding.priceAllCartView.text}", Snackbar.LENGTH_LONG).show()
                deleteAllItems()
                /*val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)*/
            }
        }

    }// fonction qui permet de lire mon fichier cart.json
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

    // fonction qui permet de calculer le prix total d'achat lors du clique sur le boutton
    @SuppressLint("SetTextI18n")
    private fun calculateTotalPrice() {
        var totalPrice = 0.0
        if (cartContainer.cartItemsList.isNotEmpty()) {
            for (cartItem in cartContainer.cartItemsList) {
                totalPrice += cartItem.items.prices[0].price * cartItem.quantity
            }
            binding.priceAllCartView.visibility = View.VISIBLE
            binding.priceAllCartView.text = "Buy For " +totalPrice.toString() + "€"
        }
        else{
            binding.priceAllCartView.visibility = View.GONE
            binding.TitleCart.text = "Votre panier est vide"

        }


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

    // check if one items in the cart is whith quantity = 0
    private fun checkQuantity() {
        for (cartItem in cartContainer.cartItemsList) {
            if (cartItem.quantity == 0) {
                cartContainer.cartItemsList.remove(cartItem)
                // remove item from json file
                val jsonFile = File(filesDir, "cart.json")
                jsonFile.delete()
                val json = Gson().toJson(cartContainer)
                jsonFile.writeText(json)
                val adapter = binding.recyclerviewCart.adapter as CartAdapter
                adapter.refreshList(cartContainer.cartItemsList)
                calculateTotalPrice()

            }
        }
    }

    // fonction qui permet de check si il y a un element est à 0 ou non
    override fun onResume() {
        super.onResume()
        readFromFile()
        checkQuantity()
        calculateTotalPrice()
        recyclerViewRefresh()

    }

    //
    override fun onDestroy() {
        super.onDestroy()
        Log.d ("onDestroy", "$this onDestroy")
    }

    //fonction qui permet d'utiliser le recycler view et donc refresh le recycler viex
    private fun recyclerViewRefresh(){
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
    }
}