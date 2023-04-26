package com.yahmedwiem.contacts.data.datasource.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yahmedwiem.contacts.data.ContactDataModel

@Entity(
    tableName = "contact_table"
)
data class ContactEntity(
    @PrimaryKey val id: String,
    val name: String,
    val phones: String,
    val email: String,
    val imageUri: String
)

fun ContactEntity.toData() = ContactDataModel(id, name, phones, email, imageUri)