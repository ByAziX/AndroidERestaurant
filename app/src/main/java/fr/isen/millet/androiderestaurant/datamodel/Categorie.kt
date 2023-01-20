package fr.isen.millet.androiderestaurant.datamodel

data class Categorie(
    val name_fr: String,
    val name_en: String,
    val items : ArrayList<Items>
) : java.io.Serializable
