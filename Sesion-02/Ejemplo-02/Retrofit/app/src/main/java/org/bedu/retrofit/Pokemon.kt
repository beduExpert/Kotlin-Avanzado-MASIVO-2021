package org.bedu.retrofit

import com.google.gson.annotations.SerializedName

data class Pokemon (
    val name : String? = "",
    val weight: Int? = 0,
    val sprites : sprites? = null

)


data class sprites(
    @SerializedName("front_default")
    val photoUrl : String? = ""
)