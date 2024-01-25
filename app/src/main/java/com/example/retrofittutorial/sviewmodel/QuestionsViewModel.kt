package com.example.retrofittutorial.sviewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.retrofittutorial.smodels.DummyDataProvider
import com.example.retrofittutorial.smodels.Question

class QuestionsViewModel : ViewModel() {
    val questionsResponse: MutableState<List<Question>> = mutableStateOf(emptyList())
    val loading: MutableState<Boolean> = mutableStateOf(false)
    val error: MutableState<String?> = mutableStateOf(null)

    fun getQuestions(){
        questionsResponse.value = DummyDataProvider.getDummyData(30)
        loading.value = false
        error.value = null
    }

    private fun onError(message: String){
        error.value = message
        loading.value = true
    }


}