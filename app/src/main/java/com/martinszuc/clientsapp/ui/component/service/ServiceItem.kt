package com.martinszuc.clientsapp.ui.component.service

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
fun ServiceItem(
    service: Service,
    clientName: String,
    category: ServiceCategory?,
    type: ServiceType?
) {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    val formattedDate = dateFormat.format(service.date)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            category?.let {
                Row {
                    Text(text = "${it.emoji} ${it.name}", style = MaterialTheme.typography.bodyMedium)
                    type?.let { type ->
                        Text(text = " • ", style = MaterialTheme.typography.bodyMedium)
                        Text(text = "${type.emoji} ${type.name}", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
            Text(text = stringResource(R.string.popis, service.description))
            Text(text = stringResource(R.string.cena, service.price.toString()))
            Text(text = stringResource(R.string.d_tum, formattedDate))
            Text(
                text = clientName,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
