package org.bedu.retrofit

data class Type (
    val name: String,
    val moves: ArrayList<Move>
)

data class Move (
    val name: String? = ""
)