package fr.isen.millet.androiderestaurant.datamodel

data class Dish(
    val id: String,
    val name: String,
    val price: String,
    val image: String,
    val description: String,
    val id_category: String
)
