package com.zheng.home.injection.module

import com.zheng.home.data.remote.QuizApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = arrayOf(NetworkModule::class))
class ApiModule {

    @Provides
    @Singleton
    internal fun provideQuizApi(@Named("quiz") retrofit: Retrofit): QuizApi =
            retrofit.create(QuizApi::class.java)
}