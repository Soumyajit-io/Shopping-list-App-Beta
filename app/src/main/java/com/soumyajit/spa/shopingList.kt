package com.soumyajit.spa

import android.icu.text.CaseMap
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class ShoppingIteam(val num:Int,
                         var name :String,
                         var quantity:Int,
                         var isEditing: Boolean = false

)

@Composable
fun MainFunction(){
    Box(
        modifier=Modifier.fillMaxSize()
            .padding(30.dp)
    ) {










        var SIteam by remember { mutableStateOf(listOf<ShoppingIteam>()) }
        var DialogShow by remember { mutableStateOf(false) }
        var IteamName by remember {mutableStateOf("")}
        var IteamQunt by remember {mutableStateOf("")}
        var Context = LocalContext.current



        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {DialogShow=true},
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            {
                Text("Add Iteam")
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            )
            {
                items(SIteam){

                }
            }
        }

        if(DialogShow){
            AlertDialog(onDismissRequest = {DialogShow = false},
                confirmButton = {
                    Row(modifier = Modifier.fillMaxWidth().padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween){
                        Button(onClick = {DialogShow = false}){
                            Text("Cancel")
                        }
                        var Context = LocalContext.current
                        Button(onClick = {
                            if(IteamName.isNotBlank()){
                                var newItem = ShoppingIteam(
                                    num = SIteam.size+1,
                                    name = IteamName,
                                    quantity = IteamQunt.toInt(),)
                                SIteam+=newItem
                                DialogShow = false
                                IteamName = ""

                                Toast.makeText(Context,"Added Successfully", Toast.LENGTH_LONG).show()

                            }

                        }) {
                            Text("Save")
                        }

                    }
                },
                title = {Text("Add Shopping Items") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = IteamName,
                            onValueChange = { IteamName = it },
                            label = { Text("Enter Item") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth().padding(10.dp)
                        )

                        OutlinedTextField(
                            value = IteamQunt,
                            onValueChange = { IteamQunt = it },
                            label = { Text("Enter Quantity") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth().padding(10.dp)
                        )
                    }
                }

            )
        }

    }

}
@Preview(showBackground = true)
@Composable
fun MainFunctionTheme(){
    MainFunction()

}