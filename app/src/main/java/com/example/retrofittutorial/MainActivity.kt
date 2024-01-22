package com.example.retrofittutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.retrofittutorial.model.TYPE_CATEGORY
import com.example.retrofittutorial.ui.theme.RetrofitTutorialTheme
import com.example.retrofittutorial.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         val viewModel: MainViewModel = MainViewModel()
        viewModel.fetchData()
        setContent {
            RetrofitTutorialTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                   LazyColumn{
                       items(viewModel.apiResponse.value){
                            if(it.type == TYPE_CATEGORY){
                                Text(text = it.key, style = MaterialTheme.typography.headlineMedium)
                            }else {
                                Surface {
                                    Column {
                                        Text(text = it.key)
                                        Text(text = it.value)
                                    }
                                }
                            }
                       }
                   }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RetrofitTutorialTheme {
        Greeting("Android")
    }
}