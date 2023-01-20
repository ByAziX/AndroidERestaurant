package fr.isen.millet.androiderestaurant

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.millet.androiderestaurant.datamodel.Items

class CustomDetailsAdapter(private val images: ArrayList<String>) : RecyclerView.Adapter<CustomDetailsAdapter.ViewHolder>() {

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageDetails)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.details_view_images, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemsViewModel = images[position]
        if (itemsViewModel != "") {
            Picasso.get().load(itemsViewModel).into(holder.imageView)
        }


    }



}
