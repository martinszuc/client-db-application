package com.martinszuc.clientsapp.ui.component.service.category_type

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.widget.addTextChangedListener
import androidx.emoji2.widget.EmojiEditText

@Composable
fun EmojiTextField(
    value: String,
    onValueChange: (String) -> Unit
) {
    val context = LocalContext.current
    val editText = remember { EmojiEditText(context) }

    AndroidView(
        factory = { context ->
            EmojiEditText(context).apply {
                setText(value)
                setOnFocusChangeListener { v, hasFocus ->
                    if (hasFocus) {
                        // Show the keyboard when the EditText is focused
                        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT)
                    }
                }
                addTextChangedListener {
                    onValueChange(it.toString())
                }
            }
        },
        update = { view ->
            view.setText(value)
            view.setSelection(value.length)
        }
    )
}