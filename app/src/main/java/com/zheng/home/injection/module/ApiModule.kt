package com.zheng.home.injection.module

import dagger.Module
import dagger.Provides
import com.zheng.home.data.remote.PokemonApi
import com.zheng.home.data.remote.QuizApi
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = arrayOf(NetworkModule::class))
class ApiModule {

    @Provides
    @Singleton
    internal fun providePokemonApi(@Named("pokemon") retrofit: Retrofit): PokemonApi =
            retrofit.create(PokemonApi::class.java)

    @Provides
    @Singleton
    internal fun provideQuizApi(@Named("quiz") retrofit: Retrofit): QuizApi =
            retrofit.create(QuizApi::class.java)
}