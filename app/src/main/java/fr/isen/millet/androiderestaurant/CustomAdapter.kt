import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.millet.androiderestaurant.R
import fr.isen.millet.androiderestaurant.datamodel.Plat

class CustomAdapter(private val list: ArrayList<Plat>, private val OnItemClickListener: (String,String,String) -> Unit) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    // adapter conteneur
    // RecyclerView contenu
    // Holds the views for adding it to text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        // val imageView: ImageView = itemView.findViewById(R.id.imageview)
        val textView: TextView = itemView.findViewById(R.id.textView)
        val imageView: ImageView = itemView.findViewById(R.id.imageview)
        val PricesView: TextView = itemView.findViewById(R.id.PricesView)
    }
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_design, parent, false)
        return ViewHolder(view)
    }

    // binds the list items to a view
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemsViewModel = list[position]
        holder.textView.text = itemsViewModel.name_fr
        holder.PricesView.text = itemsViewModel.prices[0].price.toString() + "€"



        if (itemsViewModel.images[0] != "") {

            Picasso.get().load(itemsViewModel.images[0]).into(holder.imageView)
            Log.d("image", itemsViewModel.images[0])

        }




        holder.itemView.setOnClickListener{
            OnItemClickListener(holder.textView.text as String, holder.PricesView.text as String, itemsViewModel.images[0])
        }

    }

    // return the number of the items in the list
    override fun getItemCount(): Int = list.size



}

