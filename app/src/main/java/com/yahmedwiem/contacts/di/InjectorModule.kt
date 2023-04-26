package com.yahmedwiem.contacts.di

import android.content.Context
import androidx.room.Room
import com.yahmedwiem.contacts.data.ContactRepository
import com.yahmedwiem.contacts.data.ContactRepositoryImpl
import com.yahmedwiem.contacts.data.datasource.ContactDao
import com.yahmedwiem.contacts.data.datasource.ContactDatabase
import com.yahmedwiem.contacts.data.datasource.ContactLocalDataSource
import com.yahmedwiem.contacts.domain.FetchLocalContactUseCase
import com.yahmedwiem.contacts.domain.FilterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class InjectorModule {

    @Provides
    @Singleton
    fun providesFetchLocalContactUseCase(repository: ContactRepository) =
        FetchLocalContactUseCase(repository)

    @Provides
    @Singleton
    fun providesFilterUseCase(repository: ContactRepository) =
        FilterUseCase(repository)

    @Provides
    @Singleton
    fun providesContactLocalDataSource(@ApplicationContext context: Context) =
        ContactLocalDataSource(
            context
        )

    @Provides
    @Singleton
    fun providesContactDao(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        ContactDatabase::class.java,
        "contact_db"
    ).build()
        .contactDao()

    @Provides
    @Singleton
    fun providesContactRepository(
        contactLocalDataSource: ContactLocalDataSource,
        dao: ContactDao
    ): ContactRepository = ContactRepositoryImpl(
        contactLocalDataSource,
        dao
    )
}