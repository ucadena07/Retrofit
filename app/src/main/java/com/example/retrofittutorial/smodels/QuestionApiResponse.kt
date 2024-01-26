package com.example.retrofittutorial.smodels

import android.text.Html
import com.google.gson.annotations.SerializedName

data class ResponseWrapper<T>(
    val items: List<T>
)

data class Question(
    @SerializedName("question_id")
    val questionId: Int?,
    val title: String?,
    val score: String?,
    @SerializedName("creation_date")
    val date: Long?

)

fun convertTitle(title: String?) : String{
    return Html.fromHtml(title, Html.FROM_HTML_MODE_LEGACY).toString()
}

