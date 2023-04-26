package com.yahmedwiem.contacts.domain

import com.yahmedwiem.contacts.data.ContactRepository
import com.yahmedwiem.contacts.data.toDomain

class FilterUseCase(
    private val repository: ContactRepository
) {

    suspend fun execute(query: String): List<ContactDomainModel> {
        return repository.filter(query).map { it.toDomain() }
    }
}