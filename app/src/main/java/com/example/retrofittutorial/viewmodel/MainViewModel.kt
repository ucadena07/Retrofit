package com.example.retrofittutorial.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.retrofittutorial.model.Item
import com.example.retrofittutorial.model.TYPE_CATEGORY
import com.example.retrofittutorial.model.TYPE_ITEM
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        GlobalScope.launch(Dispatchers.Main) {
            onError("Exception: ${throwable.localizedMessage}")
        }
    }



    val apiResponse: MutableState<List<Item>> = mutableStateOf(emptyList())
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String?>()

    fun fetchData() {
        apiResponse.value = arrayListOf(
            Item("Category1", "", TYPE_CATEGORY),
            Item("Key1", "Value1", TYPE_ITEM),
            Item("Key2", "Value2", TYPE_ITEM),
            Item("Category2", "", TYPE_CATEGORY),
            Item("Key3", "Value3", TYPE_ITEM),
            Item("Key4", "Value4", TYPE_ITEM)
        )
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