package com.junatrade.przemcomposeapp2

import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.junatrade.przemcomposeapp2.ui.theme.PrzemComposeApp2Theme

class MainActivity : ComponentActivity() {
    private val mainVM by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PrzemComposeApp2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val contacts = mainVM.getContacts().collectAsState(initial = emptyList())
//                    Text(text = contacts.value.toString())
                    ContactList(contacts = contacts.value,
                    onDelete = {contact ->  mainVM.deleteContact(contact)})
                }
            }
        }
    }
}

@Composable
fun ContactList(contacts: List<Contact>, onDelete: ((Contact)-> Unit)? = null) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        ContactLazyColumn(contacts, onDelete)
    }
}

@Composable
fun ContactLazyColumn(contacts: List<Contact>,
                      onDelete: ((Contact)-> Unit)? = null)
{
    LazyColumn(){
        items(items = contacts, key = {it.uid}){contact ->
            ContactRow(contact, onDelete)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ContactRow(contact: Contact, onDelete: ((Contact)-> Unit)? = null) {

    val dismissState = rememberDismissState(confirmStateChange = {
        if (it == DismissValue.DismissedToStart) {
            onDelete?.invoke(contact)
        }
        true
    })

    SwipeToDismiss(state = dismissState, 
        background = {
                     Row(modifier = Modifier
                         .fillMaxWidth()
                         .padding(start = 100.dp, end = 10.dp, top = 2.dp, bottom = 2.dp)
                         .background(Color.Red),
                     horizontalArrangement = Arrangement.End
                     ) {
                        val imageVector = ImageVector.vectorResource(id = R.drawable.baseline_delete_sweep_24)
                         Icon(imageVector = imageVector, contentDescription = null)
                     }
        }, 
        dismissThresholds = {
        FractionalThreshold(
            0.5f
        )
    }, directions = setOf(DismissDirection.EndToStart) ) {
        Surface(modifier = Modifier
            .fillMaxWidth()
            .padding(1.dp),
            shape = RoundedCornerShape(10.dp),
            elevation = 1.dp) {
            Row(modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "${contact.name} ${contact.surname}", fontSize = 18.sp)
                Text(text = contact.number, fontStyle = FontStyle.Italic)
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun ContactRowPreview() {
    ContactRow(contact = contact1)
}




