package com.yahmedwiem.contacts.data

import com.yahmedwiem.contacts.data.datasource.model.ContactEntity
import com.yahmedwiem.contacts.domain.ContactDomainModel

data class ContactDataModel(
    val id: String,
    val name: String,
    val phoneNumber: String,
    val emails: String,
    val imageUri: String
)

fun ContactDataModel.toDomain() = ContactDomainModel(
    id, name, phoneNumber, emails, imageUri
)

fun ContactDataModel.toEntity() = ContactEntity(
    id, name, phoneNumber, emails, imageUri
)