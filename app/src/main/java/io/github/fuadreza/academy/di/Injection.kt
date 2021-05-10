package io.github.fuadreza.academy.di

import android.content.Context
import io.github.fuadreza.academy.data.source.AcademyRepository
import io.github.fuadreza.academy.data.source.remote.RemoteDataSource
import io.github.fuadreza.academy.utils.JsonHelper

object Injection {
    fun provideRepository(context: Context): AcademyRepository {

        val remoteDataSource = RemoteDataSource.getInstance(JsonHelper(context))

        return AcademyRepository.getInstance(remoteDataSource)
    }
}