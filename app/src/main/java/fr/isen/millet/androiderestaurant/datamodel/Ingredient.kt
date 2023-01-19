package fr.isen.millet.androiderestaurant.datamodel

import java.util.*

data class Ingredient(
    val id:Int,
    val id_shop:Int,
    val name_fr:String,
    val name_en:String,
    val create_date: String,
    val update_date: String
)
