package com.martinszuc.clientsapp.ui.component.client.profile.services_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.martinszuc.clientsapp.R
import com.martinszuc.clientsapp.data.entity.Service
import com.martinszuc.clientsapp.data.entity.ServiceCategory
import com.martinszuc.clientsapp.data.entity.ServiceType
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ProfileServiceItem(
    service: Service,
    category: ServiceCategory?,
    type: ServiceType?,
    onClick: (Int) -> Unit  // Add onClick to handle navigation
) {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    val formattedDate = dateFormat.format(service.date)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(service.id) },  // Make card clickable and trigger navigation
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = stringResource(R.string.popis, service.description))
            Text(text = stringResource(R.string.cena, service.price.toString()))
            Text(text = stringResource(R.string.d_tum, formattedDate))
        }
    }
}
