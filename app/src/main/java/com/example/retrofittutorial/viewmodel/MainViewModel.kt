package com.example.retrofittutorial.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AndroidViewModel
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject
import javax.security.auth.callback.Callback


class MainViewModel(application: Application) : AndroidViewModel(application) {
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


        val call = ApiCallService.call(getApplication())
            call.enqueue(object : retrofit2.Callback<Person>{
            override fun onResponse(
                call: Call<Person>,
                response: Response<Person>
            ) {
                val body = response.body()
                val header = response.headers()
                val message = response.message()
                val code = response.code()
                val isSuccessful = response.isSuccessful
                val raw = response.raw()
                val errorBody = response.errorBody()


                Log.d("NETWORK-BODY",body.toString())
                Log.d("NETWORK-HEADER",header.toString())
                Log.d("NETWORK-MESSAGE",message.toString())
                Log.d("NETWORK-code",code.toString())
                Log.d("NETWORK-isSuccessful",isSuccessful.toString())
                Log.d("NETWORK-raw",raw.toString())
                Log.d("NETWORK-errorBody",errorBody.toString())


            }

            override fun onFailure(call: Call<Person>, t: Throwable) {
                if(call.isCanceled){
                    Log.d("NETWORK CALL CANCELED","${t.localizedMessage}")
                }else{
                    Log.d("NETWORK ERROR","${t.localizedMessage}")
                }


               onError(t.message.toString())
            }
        })


        error.value = null
        loading.value = false
    }

    fun fetchDataSync(){
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            var response = ApiCallService.call(getApplication()).execute()
            withContext(Dispatchers.Main){
                if(response.isSuccessful){
                    val body = response.body()
                    val header = response.headers()
                    val message = response.message()
                    val code = response.code()
                    val isSuccessful = response.isSuccessful
                    val raw = response.raw()
                    val errorBody = response.errorBody()


                    Log.d("NETWORK-BODY",body.toString())
                    Log.d("NETWORK-HEADER",header.toString())
                    Log.d("NETWORK-MESSAGE",message.toString())
                    Log.d("NETWORK-code",code.toString())
                    Log.d("NETWORK-isSuccessful",isSuccessful.toString())
                    Log.d("NETWORK-raw",raw.toString())
                    Log.d("NETWORK-errorBody",errorBody.toString())
                }else{
                    onError(response.message())
                }
            }
        }

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