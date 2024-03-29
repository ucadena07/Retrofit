package com.example.retrofittutorial.sviewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.retrofittutorial.smodels.DummyDataProvider
import com.example.retrofittutorial.smodels.Question
import com.example.retrofittutorial.smodels.ResponseWrapper
import com.example.retrofittutorial.smodels.StackOverFlowApi
import com.example.retrofittutorial.smodels.StackOverFlowService
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuestionsViewModel : ViewModel() {
    val questionsResponse: MutableState<List<Question>> = mutableStateOf(emptyList())
    val loading: MutableState<Boolean> = mutableStateOf(false)
    val error: MutableState<String?> = mutableStateOf(null)

    var page = 0

    fun getNextPage(){
        loading.value = true
        page++
        getQuestions()
        loading.value = false
    }




    fun getQuestions(){
        StackOverFlowService.api.getQuestions(page)
            .enqueue(object : Callback<ResponseWrapper<Question>>{
                override fun onResponse(
                    call: Call<ResponseWrapper<Question>>,
                    response: Response<ResponseWrapper<Question>>
                ) {
                    if(response.isSuccessful){
                        Log.d("NET", response.body().toString())
                        val questions = response.body()
                   
                        questions?.let {
                            questionsResponse.value =
                                questionsResponse.value.toList().union(it.items.toList()).toList()
                            loading.value = false
                            error.value = null
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseWrapper<Question>>, t: Throwable) {
                    Log.d("NET ERROR", t.localizedMessage)
                    onError(t.localizedMessage)
                }

            })

    }

    private fun onError(message: String){
        error.value = message
        loading.value = true
    }


}