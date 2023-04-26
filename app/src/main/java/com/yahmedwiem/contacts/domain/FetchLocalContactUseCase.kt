package com.yahmedwiem.contacts.domain

import com.yahmedwiem.contacts.data.ContactRepository
import com.yahmedwiem.contacts.data.toDomain

class FetchLocalContactUseCase(
    private val repository: ContactRepository,
) {

    suspend fun execute(): List<ContactDomainModel> {
        val contacts = repository.fetchContactFromContactList()
        repository.addContacts(contacts)
        return repository.loadContact().map { it.toDomain() }
    }
}