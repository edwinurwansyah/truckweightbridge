package com.example.truckweightbridge.di

import com.example.truckweightbridge.repository.local.TicketDao
import com.example.truckweightbridge.repository.remote.FirebaseDatabaseApi
import com.example.truckweightbridge.usecase.TicketUseCase
import com.example.truckweightbridge.usecase.TicketUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    fun provideTicketUseCase(firebaseDatabaseApi: FirebaseDatabaseApi, ticketDao: TicketDao): TicketUseCase {
        return TicketUseCaseImpl(firebaseDatabaseApi, ticketDao)
    }
}