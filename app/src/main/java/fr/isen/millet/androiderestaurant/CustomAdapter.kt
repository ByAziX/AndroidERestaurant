import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.isen.millet.androiderestaurant.R

class CustomAdapter(private val list: Array<String>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    // adapter conteneur
    // RecyclerView contenu

    // Holds the views for adding it to text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        // val imageView: ImageView = itemView.findViewById(R.id.imageview)
        val textView: TextView = itemView.findViewById(R.id.textView)
    }
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_design, parent, false)
        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemsViewModel = list[position]
        // sets the image to the imageview from our itemHolder class
       // holder.imageView.setImageResource(itemsViewModel.image)
        // sets the text to the textview from our itemHolder class
        //holder.textView.text = itemsViewModel.text
        holder.textView.text = itemsViewModel

    }

    // return the number of the items in the list
    override fun getItemCount(): Int = list.size
}
