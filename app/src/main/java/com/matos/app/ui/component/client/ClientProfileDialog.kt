package com.matos.app.ui.component.client//package com.matos.app.ui.component.client.composable
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.matos.app.ui.viewmodel.SharedClientViewModel
//
//@Composable
//fun ClientProfileDialog(
//    sharedClientViewModel: SharedClientViewModel = viewModel(),
//    onDismissRequest: () -> Unit
//) {
//    val selectedClient by sharedClientViewModel.selectedClient.observeAsState()
//
//    selectedClient?.let { client ->
//        Column(
//            modifier = Modifier
//                .padding(16.dp)
//                .fillMaxWidth()
//        ) {
//            Text(text = "Name: ${client.name}")
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(text = "Phone: ${client.phone}")
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(text = "Email: ${client.email}")
//        }
//    }
//}