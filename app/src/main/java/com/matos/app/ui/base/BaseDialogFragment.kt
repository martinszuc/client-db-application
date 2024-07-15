package com.matos.app.ui.base

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

abstract class BaseDialogFragment : DialogFragment() {

    protected val calendar = Calendar.getInstance()

    protected fun showDatePicker(onDateSet: (year: Int, month: Int, dayOfMonth: Int) -> Unit) {
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                onDateSet(year, month, dayOfMonth)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    protected fun showTimePicker(onTimeSet: (hourOfDay: Int, minute: Int) -> Unit) {
        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { _, hourOfDay, minute ->
                onTimeSet(hourOfDay, minute)
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        )
        timePickerDialog.show()
    }

    protected fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    protected fun formatDate(format: String, date: Long): String {
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        return sdf.format(date)
    }

    protected fun updateDateInView(format: String): String {
        return formatDate(format, calendar.timeInMillis)
    }

    protected fun updateTimeInView(format: String): String {
        return formatDate(format, calendar.timeInMillis)
    }
}
