package io.github.fuadreza.academy.di

import android.content.Context
import io.github.fuadreza.academy.data.source.AcademyRepository
import io.github.fuadreza.academy.data.source.local.AcademyDatabase
import io.github.fuadreza.academy.data.source.local.LocalDataSource
import io.github.fuadreza.academy.data.source.remote.RemoteDataSource
import io.github.fuadreza.academy.utils.AppExecutors
import io.github.fuadreza.academy.utils.JsonHelper

object Injection {
    fun provideRepository(context: Context): AcademyRepository {

        val database = AcademyDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance(JsonHelper(context))
        val localDataSource = LocalDataSource.getInstance(database.academyDao())
        val appExecutors = AppExecutors()

        return AcademyRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}