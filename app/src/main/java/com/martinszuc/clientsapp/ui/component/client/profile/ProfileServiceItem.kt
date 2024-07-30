package com.martinszuc.clientsapp.ui.component.client.profile

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
    type: ServiceType?
) {
    val dateFormat = SimpleDateFormat("HH:mm dd/MM/yy", Locale.getDefault())
    val formattedDate = dateFormat.format(service.date)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "${category?.emoji.orEmpty()} ${category?.name.orEmpty()}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = "${type?.emoji.orEmpty()} ${type?.name.orEmpty()}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Text(text = stringResource(R.string.popis, service.description))
            Text(text = stringResource(R.string.cena, service.price.toString()))
            Text(text = stringResource(R.string.d_tum, formattedDate))
        }
    }
}