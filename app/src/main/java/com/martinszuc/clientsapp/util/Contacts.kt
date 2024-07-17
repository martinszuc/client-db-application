package com.martinszuc.clientsapp.util

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract

data class ContactInfo(val name: String, val phone: String, val email: String)

fun getContactInfo(context: Context, uri: Uri): ContactInfo {
    var name = ""
    var phone = ""
    var email = ""

    val cursor: Cursor? = context.contentResolver.query(uri, null, null, null, null)
    cursor?.use {
        if (it.moveToFirst()) {
            val nameIndex = it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
            name = if (nameIndex != -1) it.getString(nameIndex) else ""

            val idIndex = it.getColumnIndex(ContactsContract.Contacts._ID)
            val contactId = if (idIndex != -1) it.getString(idIndex) else ""

            val hasPhoneIndex = it.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)
            val hasPhone = if (hasPhoneIndex != -1) it.getInt(hasPhoneIndex) > 0 else false

            if (hasPhone) {
                val phonesCursor: Cursor? = context.contentResolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                    arrayOf(contactId),
                    null
                )
                phonesCursor?.use { pc ->
                    if (pc.moveToFirst()) {
                        val phoneIndex = pc.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                        phone = if (phoneIndex != -1) pc.getString(phoneIndex) else ""
                    }
                }
            }

            val emailCursor: Cursor? = context.contentResolver.query(
                ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                arrayOf(contactId),
                null
            )
            emailCursor?.use { ec ->
                if (ec.moveToFirst()) {
                    val emailIndex = ec.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS)
                    email = if (emailIndex != -1) ec.getString(emailIndex) else ""
                }
            }
        }
    }

    return ContactInfo(name, phone, email)
}