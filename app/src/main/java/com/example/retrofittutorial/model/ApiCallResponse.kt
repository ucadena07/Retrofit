package com.example.retrofittutorial.model

data class ApiCallResponse(
    val method: String?,
    val query: Map<String,String>?,
    val headers: Map<String,String>?,

)
