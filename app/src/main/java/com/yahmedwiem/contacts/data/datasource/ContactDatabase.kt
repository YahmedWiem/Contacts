package com.yahmedwiem.contacts.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yahmedwiem.contacts.data.datasource.model.ContactEntity


@Database(
    entities = [ContactEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ContactDatabase : RoomDatabase() {

    abstract fun contactDao(): ContactDao
}