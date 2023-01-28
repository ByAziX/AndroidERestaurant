package fr.isen.millet.androiderestaurant.pages

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
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
    private var quantityCount: Int = 0
    private var quantityTotal: Int = 1
    private lateinit var cartContainer: CartContainer
    private var ingredients = ""

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        val cartItem=menu?.findItem(R.id.action_cart)
        val cartView=cartItem?.actionView

        cartView?.setOnClickListener {
            onOptionsItemSelected(cartItem)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.action_cart -> {
                val intent = Intent(this, CartActivity::class.java)
                startActivity(intent)
            }


        }
        return super.onOptionsItemSelected(item)
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
        supportActionBar?.title= items.name_en



        if (fileExists()){
            readFromFile()
            priceAndQuantityToCart(items)

        } else {
            cartContainer = CartContainer(mutableListOf())
            quantityTotal = 1
        }

        priceAndQuantityToCart(items)

        for (i in items.ingredients) {
            Log.i("Ingredients", i.name_fr)
            ingredients += i.name_fr + " "
        }

        binding.ingrediensDetails.text = ingredients

        val viewPager = binding.viewPagerDetailsImages
        val adapter = CustomDetailsAdapter(items.images as ArrayList<String>)

        viewPager.adapter = adapter

        binding.buttonIncrease.setOnClickListener {
            quantityCount++
            priceAndQuantityToCart(items)
        }

        binding.buttonDecrease.setOnClickListener {
            if (quantityCount > 1) {
                quantityCount--
                priceAndQuantityToCart(items)
            }
        }

        binding.buttonPriceDetails.setOnClickListener {
            addDishToCart(items)
        }
    }

@SuppressLint("SetTextI18n")
fun priceAndQuantityToCart(items: Items){
    for (i in cartContainer.cartItemsList) {
        if (i.items.id == items.id) {
            quantityTotal = i.quantity + quantityCount
            binding.buttonPriceDetails.text = "Total Price " + (items.prices[0].price * (quantityTotal)).toString() + "€"
            binding.textViewQuantity.text = (quantityTotal).toString()
        }}
}

     private fun addDishToCart(items: Items) {

         Snackbar.make(binding.root, "Dish added to cart", Snackbar.LENGTH_SHORT).show()

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
     }

    private fun writeInFile() {
        val json = GsonBuilder().setPrettyPrinting().create().toJson(cartContainer)
        val file = File(filesDir, "cart.json")
        file.writeText(json)
    }

    private fun readFromFile() {
        val jsonFile = File(filesDir, "cart.json")
        val jsonContent = jsonFile.readText()
        val gson = Gson()
        cartContainer = gson.fromJson(jsonContent, CartContainer::class.java)
    }


    fun fileExists(): Boolean {
        val file = File(filesDir, "cart.json")
        return file.exists()
    }
}