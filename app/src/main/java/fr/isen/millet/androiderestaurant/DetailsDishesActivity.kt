package fr.isen.millet.androiderestaurant

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import fr.isen.millet.androiderestaurant.CustomDetailsAdapter
import fr.isen.millet.androiderestaurant.databinding.ActivityDetailsDishesBinding
import fr.isen.millet.androiderestaurant.datamodel.CartContainer
import fr.isen.millet.androiderestaurant.datamodel.CartItems
import fr.isen.millet.androiderestaurant.datamodel.Items


class DetailsDishesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsDishesBinding
    private var quantity = 1
    private var price = 0.0
    private lateinit var cartContainer: CartContainer
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsDishesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val items = intent.extras?.getSerializable("Items") as Items
        binding.titleDetailsDishes.text = items.name_fr ?: "No Categorie title found"
        title=items.name_fr
        binding.priceDetailsDishes.text = items.prices[0].price.toString() + "€"
        binding.buttonPriceDetails.text = "Total Price " + (items.prices[0].price).toString() + "€"

        var ingredients = ""
        for (i in items.ingredients) {
            Log.i("Ingredients", i.name_fr)
            ingredients += i.name_fr + " "
        }



        binding.ingrediensDetails.text = ingredients



        val viewPager = binding.viewPagerDetailsImages
        val adapter = CustomDetailsAdapter(items.images as ArrayList<String>)
        viewPager.adapter = adapter

        binding.buttonIncrease.setOnClickListener {
            IncreasePriceAndQuantityToCart(items)
        }

        binding.buttonDecrease.setOnClickListener {
            decreasePriceAndQuantityToCart(items)
        }


        binding.buttonPriceDetails.setOnClickListener {
            addDishToCart(items)
        }


        //val adapter = ViewPagerAdapter(this, items.images)


    }




    @SuppressLint("SetTextI18n")
    fun IncreasePriceAndQuantityToCart( items: Items) {

            quantity++
            price = items.prices[0].price * quantity
            binding.buttonPriceDetails.text = "Total Price " + (price).toString() + "€"
            binding.textViewQuantity.text = quantity.toString()


    }

    fun decreasePriceAndQuantityToCart( items: Items) {


            if (quantity > 1) {
                quantity--
                price = items.prices[0].price * quantity
                binding.buttonPriceDetails.text = "Total Price " + (price).toString() + "€"
                binding.textViewQuantity.text = quantity.toString()
            }

        }

     private fun addDishToCart(items: Items) {

         Snackbar.make(binding.root, "Dish added to cart", Snackbar.LENGTH_SHORT).show()

           /* if (cartContainer.cartItemsList.contains(CartItems(items, quantity))) {

                val index = cartContainer.cartItemsList.indexOf(CartItems(items, quantity))

                val item = cartContainer.cartItemsList[index]

                item.quantity += quantity

                cartContainer.cartItemsList[index] = item
            } else {
                cartContainer.cartItemsList.add(CartItems(items, quantity))

            }*/
            //cartContainer.cartItemsList.add(CartItems(items, quantity))

         /*val json = Gson().toJson(cartContainer)
         getSharedPreferences("cart", MODE_PRIVATE).edit().putString("cart", json).apply()
         Log.i("cart", json)*/

        }







}