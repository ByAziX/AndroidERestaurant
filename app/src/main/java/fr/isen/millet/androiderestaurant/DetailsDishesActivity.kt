package fr.isen.millet.androiderestaurant

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import fr.isen.millet.androiderestaurant.databinding.ActivityDetailsDishesBinding
import fr.isen.millet.androiderestaurant.datamodel.Items

class DetailsDishesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsDishesBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsDishesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val items = intent.extras?.getSerializable("Items") as Items
        binding.titleDetailsDishes.text = items.name_fr ?: "No Categorie title found"
        title=items.name_fr
        binding.priceDetailsDishes.text = items.prices[0].price.toString() ?: "No Categorie title found"

        var ingredients = ""
        for (i in items.ingredients) {
            Log.i("Ingredients", i.name_fr)
            ingredients += i.name_fr + " "
        }

        binding.ingrediensDetails.text = ingredients



        val viewPager = binding.viewPagerDetailsImages
        val adapter = CustomDetailsAdapter(items.images as ArrayList<String>)
        viewPager.adapter = adapter

        //val adapter = ViewPagerAdapter(this, items.images)


    }

}