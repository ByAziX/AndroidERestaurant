package fr.isen.millet.androiderestaurant.pages

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fr.isen.millet.androiderestaurant.CustomDetailsAdapter
import fr.isen.millet.androiderestaurant.R
import fr.isen.millet.androiderestaurant.databinding.ActivityDetailsDishesBinding
import fr.isen.millet.androiderestaurant.datamodel.CartContainer
import fr.isen.millet.androiderestaurant.datamodel.CartItems
import fr.isen.millet.androiderestaurant.datamodel.Items
import java.io.File


class DetailsDishesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsDishesBinding
    private var quantityTotal: Int = 0
    private lateinit var cartContainer: CartContainer
    private var ingredients = ""
    private var textCartItemCount: TextView? = null
    private var absTopSubMenus: Menu? = null


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        absTopSubMenus = menu
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

        if (File(filesDir, "cart.json").exists()) {
            readFromFile()

            textCartItemCount?.text = cartContainer.cartItemsList.size.toString()
        }
        else
            textCartItemCount?.text = "0"
    }
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsDishesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val items = intent.extras?.getSerializable("Items") as Items
        binding.titleDetailsDishes.text = items.name_fr
        binding.priceDetailsDishes.text = items.prices[0].price.toString() + "€"
        binding.buttonPriceDetails.text = "Total Price " + (items.prices[0].price * quantityTotal).toString() + "€"

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.subtitle = items.name_fr
        supportActionBar?.title = items.name_en


        for (i in items.ingredients) {
            Log.i("Ingredients", i.name_fr)
            ingredients += i.name_fr + " "
        }

        binding.ingrediensDetails.text = ingredients

        val viewPager = binding.viewPagerDetailsImages
        val adapter = CustomDetailsAdapter(items.images as ArrayList<String>)

        viewPager.adapter = adapter


        if (fileExists()) {
            readFromFile()

            for (i in cartContainer.cartItemsList) {
                if (i.items.id == items.id) {
                    quantityTotal = i.quantity
                    refreshPriceAndQuantityToCart(items)
                }
            }

        } else {
            cartContainer = CartContainer(mutableListOf())
            quantityTotal = 0
        }
        refreshPriceAndQuantityToCart(items)


        binding.buttonIncrease.setOnClickListener {
            quantityTotal++
            refreshPriceAndQuantityToCart(items)
        }

        binding.buttonDecrease.setOnClickListener {
            if (quantityTotal > 0) {
                quantityTotal--
                refreshPriceAndQuantityToCart(items)
            }
        }

        binding.buttonPriceDetails.setOnClickListener {
            addDishToCart(items)
        }
    }

    @SuppressLint("SetTextI18n")
    fun refreshPriceAndQuantityToCart(items: Items) {
         binding.buttonPriceDetails.text = "Total Price " + (items.prices[0].price * ( quantityTotal)).toString() + "€"
         binding.textViewQuantity.text = (quantityTotal).toString()

    }

    private fun addDishToCart(items: Items) {

        Snackbar.make(binding.root, "update du cart", Snackbar.LENGTH_SHORT).show()
        // add to cart container if not already in it and update quantity if already in it

        if (cartContainer.cartItemsList.isEmpty()) {
            cartContainer.cartItemsList.add(CartItems(items, quantityTotal))
        } else {
            var alreadyInCart = false
            for (i in cartContainer.cartItemsList) {
                if (i.items.id == items.id) {
                    i.quantity = quantityTotal
                    alreadyInCart = true
                }
            }
            if (!alreadyInCart) {
                cartContainer.cartItemsList.add(CartItems(items, quantityTotal))
            }
        }

        writeInFile()
        absTopSubMenus?.clear()
        onCreateOptionsMenu(absTopSubMenus)
    }

    private fun writeInFile() {
        val json = GsonBuilder().setPrettyPrinting().create().toJson(cartContainer)
        val file = File(filesDir, "cart.json")
        file.writeText(json)
    }

    private fun readFromFile() {

        val jsonFile = File(filesDir, "cart.json")
        cartContainer = if (jsonFile.exists()) {
            val json = jsonFile.readText()
            Gson().fromJson(json, CartContainer::class.java)
        } else {
            CartContainer(arrayListOf())
        }

    }


    private fun fileExists(): Boolean {
        val file = File(filesDir, "cart.json")
        return file.exists()
    }
}