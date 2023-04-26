package com.yahmedwiem.contacts.data.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yahmedwiem.contacts.data.datasource.model.ContactEntity


@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveContacts(contact: List<ContactEntity>)

    @Query(
        """
        SELECT * 
        FROM contact_table
    """
    )
    suspend fun loadContacts(): List<ContactEntity>


    @Query(
        """
        SELECT *
        FROM contact_table
        WHERE name LIKE '%' || :query || '%'
        OR email LIKE '%' || :query || '%'
        OR phones LIKE '%' || :query || '%'
    """
    )
    suspend fun filterContact(query: String): List<ContactEntity>
}