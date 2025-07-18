package com.soumyajit.spa

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

data class ShoppingItem(val num:Int,
                         var name :String,
                         var quantity: String,
                         var isEditing: Boolean = false

)

@Composable
fun MainFunction(){
    Box(
        modifier=Modifier.fillMaxSize()
            .padding(10.dp)
    ) {










        var SItem by remember { mutableStateOf(listOf<ShoppingItem>()) }
        var DialogShow by remember { mutableStateOf(false) }
        var ItemName by remember {mutableStateOf("")}
        var ItemQunt by remember {mutableStateOf("")}
        val Context = LocalContext.current



        Column(
            modifier = Modifier.fillMaxSize().fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {DialogShow=true},
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            {
                Text("Add Item")
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp)
            )
            {
                items(SItem){
                    item ->
                    if (item.isEditing){
                        ShoppingItemEditor(item= item, onEditComplete ={
                            Editedname,Editedqty ->
                            SItem = SItem.map{it.copy(isEditing = false)}
                            val editedItem = SItem.find{it.num == item.num}
                            editedItem?.let{
                                it.name = Editedname
                                it.quantity = Editedqty
                            }
                        })
                    }
                    else{
                        ShoppingListItem(item=item, onEditClick = {
                            //finding out which item we are editing
                            SItem=SItem.map { it.copy(isEditing =it.num==item.num) }
                        }, onDeleteClick = {
                            SItem=SItem-item
                        })
                    }
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

                        Button(onClick = {
                            if(ItemName.isNotBlank()){
                                var newItem = ShoppingItem(
                                    num = SItem.size+1,
                                    name = ItemName,
                                    quantity = ItemQunt,)
                                SItem+=newItem
                                DialogShow = false
                                ItemName = ""
                                ItemQunt = ""

                                Toast.makeText(Context,"Added Successfully", Toast.LENGTH_LONG).show()

                            }else{
                                Toast.makeText(Context,"Enter Item", Toast.LENGTH_LONG).show()
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
                            value = ItemName,
                            onValueChange = { ItemName = it },
                            label = { Text("Enter Item") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth().padding(10.dp)
                        )

                        OutlinedTextField(
                            value = ItemQunt,
                            onValueChange = { ItemQunt = it },
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

@Composable
fun ShoppingListItem (
    item: ShoppingItem,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
){
    Row(
        modifier = Modifier.fillMaxSize()
            .padding(5.dp)
            .border(border = BorderStroke(2.dp, Color(0xFF038E8D)),
                shape = RoundedCornerShape(20)),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically

    ){
        Text("${item.num}.", modifier = Modifier.padding(8.dp))
        Text("Item: ${item.name}", modifier = Modifier.padding(8.dp))
        Text("Qty: ${item.quantity}", modifier = Modifier.padding(8.dp))
        Row(modifier = Modifier
            .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ){
            IconButton(onClick = onEditClick) {
                Icon(Icons.Default.Edit, contentDescription = "")
            }
            IconButton(onClick = onDeleteClick) {
                Icon(Icons.Default.Delete, contentDescription = "")
            }
        }
    }
}
@Composable
fun ShoppingItemEditor(item: ShoppingItem,onEditComplete: (String , String ) -> Unit){
    var Editedname by remember{mutableStateOf(item.name)}
    var Editedqty by remember{mutableStateOf(item.quantity)}
    var isEditing by remember{mutableStateOf(item.isEditing)}

    Row(modifier = Modifier.fillMaxSize()
        .padding(8.dp)
        .background(Color.White),
        horizontalArrangement = Arrangement.SpaceEvenly
    ){
        Column {
            BasicTextField(value = Editedname, onValueChange = {Editedname=it},
                singleLine = true,
                modifier = Modifier.wrapContentSize().padding(8.dp))

            BasicTextField(value = Editedqty, onValueChange = {Editedqty=it},
                singleLine = true,
                modifier = Modifier.wrapContentSize().padding(8.dp))
        }
        Button(onClick = {
            isEditing=false
            onEditComplete(Editedname,Editedqty)
        }) {
            Icon(Icons.Default.Done, contentDescription = "")
        }
    }
}
