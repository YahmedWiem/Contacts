package com.yahmedwiem.contacts.data.datasource

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.provider.ContactsContract
import com.yahmedwiem.contacts.data.ContactDataModel

class ContactLocalDataSource(
    private val context: Context
) {

    @SuppressLint("Range")
    fun getContacts(): List<ContactDataModel> {
        val contacts = mutableListOf<ContactDataModel>()
        val resolver: ContentResolver = context.contentResolver
        val cursor = resolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        if (cursor != null) {
            if (cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                    val photo =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI))
                    val name =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                    val phoneNumber =
                        (cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))).toInt()

                    val emailCur = context.contentResolver.query(
                        ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                        listOf<String>(id).toTypedArray(),
                        null
                    );
                    val emailList = mutableListOf<String>()
                    while (emailCur!!.moveToNext()) {
                        val email =
                            emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA))
                        emailList.add(email)

                    }
                    emailCur.close();


                    if (phoneNumber > 0) {
                        val cursorPhone = context.contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",
                            arrayOf(id),
                            null
                        )

                        if ((cursorPhone?.count ?: 0) > 0) {
                            while (cursorPhone?.moveToNext() == true) {
                                val phoneNumValue = cursorPhone.getString(
                                    cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                                )
                                val photoUri = if (photo.isNullOrEmpty()) "" else photo
                                contacts.add(
                                    ContactDataModel(
                                        id,
                                        name,
                                        phoneNumValue,
                                        emailList.joinToString(", "),
                                        photoUri
                                    )
                                )
                            }
                        }
                        cursorPhone?.close()
                    }
                }
            } else {
                //   toast("No contacts available!")
            }
        }
        cursor?.close()
        return contacts
    }
}