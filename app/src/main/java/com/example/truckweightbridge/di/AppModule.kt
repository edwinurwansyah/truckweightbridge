package com.example.truckweightbridge.di

import android.content.Context
import androidx.room.Room
import com.example.truckweightbridge.repository.local.TicketDao
import com.example.truckweightbridge.repository.local.TicketDatabase
import com.example.truckweightbridge.repository.remote.FirebaseDatabaseApi
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): TicketDatabase {
        return Room.databaseBuilder(
            appContext,
            TicketDatabase::class.java,
            "ticket-db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTicketDao(database: TicketDatabase): TicketDao {
        return database.ticketDao()
    }

    @Provides
    @Singleton
    fun provideTicketFirebaseFirestore(): FirebaseFirestore {
        val db = FirebaseFirestore.getInstance()
        return db
    }

    @Provides
    @Singleton
    fun provideRetrofit() : Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        return Retrofit.Builder()
            .client(client)
            .baseUrl("https://firestore.googleapis.com/v1/projects/test-project-1c7d7/databases/(default)/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideFirestoreApi(retrofit: Retrofit) : FirebaseDatabaseApi{
        return retrofit.create(FirebaseDatabaseApi::class.java)
    }
}