package com.yahmedwiem.contacts.data

import com.yahmedwiem.contacts.data.datasource.ContactDao
import com.yahmedwiem.contacts.data.datasource.ContactLocalDataSource
import com.yahmedwiem.contacts.data.datasource.model.toData

class ContactRepositoryImpl(
    private val contactLocalDataSource: ContactLocalDataSource,
    private val contactDao: ContactDao
) : ContactRepository {

    override suspend fun loadContact(): List<ContactDataModel> = contactDao.loadContacts().map {
        it.toData()
    }

    override suspend fun fetchContactFromContactList(): List<ContactDataModel> =
        contactLocalDataSource.getContacts()

    override suspend fun addContacts(contacts: List<ContactDataModel>) = contactDao.saveContacts(
        contacts.map { it.toEntity() }
    )

    override suspend fun filter(query: String): List<ContactDataModel> {
        return contactDao.filterContact(query).map { it.toData() }
    }
}