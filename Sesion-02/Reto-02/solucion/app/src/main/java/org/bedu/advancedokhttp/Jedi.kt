package org.bedu.advancedokhttp

import com.google.gson.annotations.SerializedName

data class Jedi(
    val name: String? = "",
    val height: Int? = 0,
    val mass: Int? =0
)

data class JediList(
   @SerializedName("results") //el nombre real
    val jediList: ArrayList<Jedi>
)