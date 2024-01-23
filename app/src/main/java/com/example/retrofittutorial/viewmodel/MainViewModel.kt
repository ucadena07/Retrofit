package com.example.retrofittutorial.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.retrofittutorial.model.ApiCallResponse
import com.example.retrofittutorial.model.ApiCallService
import com.example.retrofittutorial.model.Item
import com.example.retrofittutorial.model.Person
import com.example.retrofittutorial.model.TYPE_CATEGORY
import com.example.retrofittutorial.model.TYPE_ITEM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject
import javax.security.auth.callback.Callback

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        GlobalScope.launch(Dispatchers.Main) {
            onError("Exception: ${throwable.localizedMessage}")
        }
    }



    val apiResponse: MutableState<List<Person>> = mutableStateOf(emptyList())
    val loading: MutableState<Boolean> = mutableStateOf(true)
    val error = MutableLiveData<String?>()

    fun fetchData() {
        Log.d("NETWORK","Fetching....")
        loading.value = true

        ApiCallService.call().enqueue(object : retrofit2.Callback<Person>{
            override fun onResponse(
                call: Call<Person>,
                response: Response<Person>
            ) {
              val body = response.body()

                Log.d("NETWORK",body.toString())


            }

            override fun onFailure(call: Call<Person>, t: Throwable) {
                Log.d("NETWORK ERROR","${t.localizedMessage}")
               onError(t.message.toString())
            }

        })

        error.value = null
        loading.value = false
    }

    private fun onError(message: String) {
        error.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}