package com.example.retrofittutorial.model

import com.google.gson.annotations.SerializedName

data class Person(
    val name: String,
    val height: String,
    val mass: String,
    @SerializedName("hair_color")
    val hairColor: String,
    @SerializedName("skin_color")
    val skinColor: String,

)
