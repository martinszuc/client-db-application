package com.martinszuc.clientsapp.ui.component.common

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.martinszuc.clientsapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarWithOptionalBackButton(
    title: String,
    onBackClick: (() -> Unit)? = null,  // Nullable back button handler
    actions: @Composable (RowScope.() -> Unit) = {}  // Optional actions slot, using RowScope
) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = if (onBackClick != null) {
            {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back)
                    )
                }
            }
        } else ({}),
        actions = actions,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Composable
fun AppBarWithoutActions(title: String) {
    AppBarWithOptionalBackButton(
        title = title
    )
}

@Composable
fun AppBarWithBackButtonAndActions(
    title: String,
    onBackClick: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit
) {
    AppBarWithOptionalBackButton(
        title = title,
        onBackClick = onBackClick,
        actions = actions
    )
}
