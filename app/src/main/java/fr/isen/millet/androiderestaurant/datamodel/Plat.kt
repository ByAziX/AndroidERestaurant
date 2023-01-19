package fr.isen.millet.androiderestaurant.datamodel

data class Plat(
    val id: Int,
    val id_category: Int,
    val name_fr: String,
    val name_en: String,
    val categories_name_fr: String,
    val categories_name_en: String,
    val images : List<String>,
    val ingredients: ArrayList<Ingredient>,
    val prices: ArrayList<Prices>
){

}
