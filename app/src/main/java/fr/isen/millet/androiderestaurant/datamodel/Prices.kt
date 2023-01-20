package fr.isen.millet.androiderestaurant.datamodel

data class Prices(
    val id: Int,
    val id_pizza: Int,
    val id_size: Int,
    val price: Double,
    val create_date: String,
    val update_date: String,
    val size: String

) : java.io.Serializable
