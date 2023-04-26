package com.yahmedwiem.contacts.data

interface ContactRepository {

    suspend fun loadContact(): List<ContactDataModel>

    suspend fun fetchContactFromContactList(): List<ContactDataModel>

    suspend fun addContacts(contacts: List<ContactDataModel>)

    suspend fun filter(query: String): List<ContactDataModel>
}