package com.example.retrofittutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.retrofittutorial.model.TYPE_CATEGORY
import com.example.retrofittutorial.smodels.convertTitle
import com.example.retrofittutorial.sviewmodel.QuestionsViewModel
import com.example.retrofittutorial.ui.theme.RetrofitTutorialTheme
import com.example.retrofittutorial.viewmodel.MainViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         val viewModel: QuestionsViewModel by viewModels()
        viewModel.getNextPage()


        setContent {
            RetrofitTutorialTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),

                    color = MaterialTheme.colorScheme.background
                ) {
                    val listState = rememberLazyListState()
                    if(!listState.canScrollForward){
                        viewModel.getNextPage()
                    }
                    if(viewModel.loading.value){
                        LinearProgressIndicator()
                    }else{
                        LazyColumn(state = listState){
                            items(viewModel.questionsResponse.value){
                                Card(modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 5.dp)
                                    .height(75.dp)) {
                                    Row(modifier = Modifier.padding(5.dp)) {
                                        Text(text = convertTitle(it.title))
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