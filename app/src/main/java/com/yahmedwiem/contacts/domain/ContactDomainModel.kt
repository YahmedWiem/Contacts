package com.yahmedwiem.contacts.domain

import com.yahmedwiem.contacts.ContactUiModel

data class ContactDomainModel(
    val id: String,
    val name: String,
    val phone: String,
    val emails: String,
    val imageUri: String
)


fun ContactDomainModel.toUi() = ContactUiModel(
    id, name, phone, emails, imageUri
)