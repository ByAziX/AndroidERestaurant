package fr.isen.millet.androiderestaurant.datamodel

data class Ingredients(
    val id:Int,
    val id_shop:Int,
    val name_fr:String,
    val name_en:String,
    val create_date: String,
    val update_date: String
): java.io.Serializable
