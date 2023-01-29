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
import fr.isen.millet.androiderestaurant.datamodel.CartItems
import fr.isen.millet.androiderestaurant.datamodel.Items

class CartAdapter(private val list: ArrayList<CartItems>, private val OnItemClickListener: (Items) -> Unit) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    // adapter conteneur
    // RecyclerView contenu
    // Holds the views for adding it to text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        // val imageView: ImageView = itemView.findViewById(R.id.imageview)
        val textView: TextView = itemView.findViewById(R.id.textView)
        val imageView: ImageView = itemView.findViewById(R.id.imageview)
        val PricesView: TextView = itemView.findViewById(R.id.PricesView)
        val QuantityView: TextView = itemView.findViewById(R.id.textQuantityCartView)
    }
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_design_cart, parent, false)
        return ViewHolder(view)
    }

    // binds the list items to a view
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemsViewModel = list[position].items
        holder.textView.text = itemsViewModel.name_fr
        holder.PricesView.text = itemsViewModel.prices[0].price.toString() + "€"
        holder.QuantityView.text = list[position].quantity.toString()


        if (itemsViewModel.images[0] != "") {
            Picasso.get().load(itemsViewModel.images[0]).into(holder.imageView)
            Log.d("image", itemsViewModel.images[0])

        }
        holder.itemView.setOnClickListener{
            OnItemClickListener(itemsViewModel)
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun refreshList(newList: MutableList<CartItems>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }
    // return the number of the items in the list
    override fun getItemCount(): Int = list.size

}

