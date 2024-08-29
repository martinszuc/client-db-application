package com.martinszuc.clientsapp.ui.component.common.items

/**
 * Project: Clients database application
 * File: CommonServiceItems
 *
 * Author: Bc. Martin Szuc (matoszuc@gmail.com)
 * GitHub: https://github.com/martinszuc
 *
 * Created on: 8/23/24 at 5:12 PM
 *
 *
 * License:
 * This code is licensed under MIT License. You may not use this file except
 * in compliance with the License.
 */

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
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
import com.martinszuc.clientsapp.utils.DateUtils

/**
 * Project: database application
 *
 * Author: Bc. Martin Szuc (matoszuc@gmail.com)
 * GitHub: https://github.com/martinszuc
 *
 *
 * License:
 * This code is licensed under MIT License. You may not use this file except
 * in compliance with the License.
 */

@Composable
fun ProfileServiceItem(
    service: Service,
    onClick: (Int) -> Unit  // Add onClick to handle navigation
) {
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
            Text(text = stringResource(R.string.d_tum, DateUtils.formatLongDate(service.date)))
        }
    }
}

@Composable
fun ServiceItem(
    service: Service,
    clientName: String,
    onClick: () -> Unit
) {
    val formattedDate = DateUtils.formatLongDateWithTime(service.date)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
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
